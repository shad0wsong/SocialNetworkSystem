package com.kuzin.feedservice.logic;

import com.kuzin.feedservice.dto.HashTagCreateRequest;
import com.kuzin.feedservice.dto.PostCreateRequest;
import com.kuzin.feedservice.dto.PostEditRequest;
import com.kuzin.feedservice.entity.HashTag;
import com.kuzin.feedservice.entity.Post;
import com.kuzin.feedservice.entity.User;
import com.kuzin.feedservice.redis.entity.RedisPost;
import com.kuzin.feedservice.repositories.HashTagRepo;
import com.kuzin.feedservice.repositories.JpaPostRepository;
import com.kuzin.feedservice.redis.repository.RedisPostRepository;
import com.kuzin.feedservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class PostLogic {

    private final JpaPostRepository jpaPostRepository;

    private final UserRepository userRepository;

    private final RedisPostRepository redisPostRepository;

    private final HashTagRepo hashTagRepo;

    public void createPost(PostCreateRequest postCreateRequest) {
        User currentSecurityUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(currentSecurityUser.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));

        List<HashTag> hashTagList = new ArrayList<>();
        postCreateRequest.getHashTagList().stream()
                .forEach(hashTag -> hashTagList.add(hashTagRepo.findByName(hashTag)));

        Post post = Post.builder()
                .text(postCreateRequest.getText())
                .base64Image(postCreateRequest.getBase64Image())
                .user(currentUser)
                .hashTagList(new ArrayList<>(hashTagList))
                .views(0L)
                .build();

        Post savedPost = jpaPostRepository.save(post);
        redisPostRepository.save(savedPost);
    }

    public List<Post> getPostsByHashTag(String hashtagName) {
        HashTag hashTag = hashTagRepo.findByName(hashtagName);
        return hashTag.getPosts();

    }

    public void createHashTag(HashTagCreateRequest request) {
        HashTag hashTag = new HashTag();
        hashTag.setName(request.getName());
        hashTagRepo.save(hashTag);
    }
    public List<Post> getTopPosts() {
        List<Post> allPosts = jpaPostRepository.findAll();
        return allPosts.stream()
                .sorted((x,y) -> (int) (y.getViews() - x.getViews()))
                .limit(5)
                .collect(Collectors.toList());
    }

    public Post getPostById(String id) {
        Post post = jpaPostRepository.findById(Long.valueOf(id)).orElse(null);
        post.setViews(post.getViews() + 1);
        jpaPostRepository.saveAndFlush(post);
        return post;
    }

    public List<Post> getPostsByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user with such email"));

        List<Post> posts = user.getPosts();
        List<Post> copy = new ArrayList<>(posts);

        ListIterator<Post> listIterator = copy.listIterator();

        while (listIterator.hasNext()) {
            Post post = listIterator.next();
            System.out.println(post);
            post.setViews(post.getViews() + 1);
            listIterator.set(post);
            jpaPostRepository.saveAndFlush(post);
        }

        return copy;
    }

    public List<Post> getCurrentUserPosts() {
        User currentSecurityUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(currentSecurityUser.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));
        currentUser.getPosts()
                .forEach(this::updateViewsAndSave);

        return currentUser.getPosts();
    }

    public List<Post> getAllPosts() {
        List<Post> posts = jpaPostRepository.findAll();
        posts.forEach(this::updateViewsAndSave);

        return posts;
    }

    public ResponseEntity editPost(PostEditRequest request) {
        ResponseEntity responseEntity;
        Post post = jpaPostRepository.findById(request.getId()).orElseThrow();
        User currentSecurityUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String postUser = post.getUser().getEmail();
        String currentUser= currentSecurityUser.getEmail();

        if(currentUser.equals(postUser)) {
            post.setText(request.getText());
            post.setBase64Image(request.getBase64Image());
            jpaPostRepository.save(post);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return responseEntity;
    }

    public ResponseEntity deletePostById(Long id) {
        ResponseEntity responseEntity;
        Post post = jpaPostRepository.findById(id).orElseThrow();
        User currentSecurityUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String postUser = post.getUser().getEmail();
        String currentUser= currentSecurityUser.getEmail();

        if(currentUser.equals(postUser)) {
            jpaPostRepository.delete(post);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return responseEntity;
    }

    public List<Post> redisPostToPost(List<RedisPost> redisPosts) {
        return redisPosts.stream()
                .map(redisPost -> new Post(redisPost.getText(), userRepository.findById(redisPost.getUserId()).orElse(null), redisPost.getBase64Image()))
                .collect(Collectors.toList());
    }

    public Post updateViewsAndSave(Post post) {
            post.setViews(post.getViews() + 1);
         return jpaPostRepository.saveAndFlush(post);
    }

}
