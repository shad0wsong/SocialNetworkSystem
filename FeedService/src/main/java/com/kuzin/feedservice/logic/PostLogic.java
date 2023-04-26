package com.kuzin.feedservice.logic;

import com.kuzin.feedservice.dto.PostCreateRequest;
import com.kuzin.feedservice.dto.PostEditRequest;
import com.kuzin.feedservice.entity.Post;
import com.kuzin.feedservice.entity.User;
import com.kuzin.feedservice.repositories.PostRepository;
import com.kuzin.feedservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PostLogic {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public void createPost(PostCreateRequest postCreateRequest) {
        User currentSecurityUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(currentSecurityUser.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));

        Post post = Post.builder()
                .text(postCreateRequest.getText())
                .base64Image(postCreateRequest.getBase64Image())
                .user(currentUser)
                .build();

        postRepository.save(post);
    }

    public List<Post> getPostsByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user with such email"));
        return user.getPosts();
    }

    public List<Post> getCurrentUserPosts() {
        User currentSecurityUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByEmail(currentSecurityUser.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));
        return currentUser.getPosts();
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public ResponseEntity editPost(PostEditRequest request) {
        ResponseEntity responseEntity;
        Post post = postRepository.findById(request.getId()).orElseThrow();
        User currentSecurityUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String postUser = post.getUser().getEmail();
        String currentUser= currentSecurityUser.getEmail();

        if(currentUser.equals(postUser)) {
            post.setText(request.getText());
            post.setBase64Image(request.getBase64Image());
            postRepository.save(post);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return responseEntity;
    }

    public ResponseEntity deletePostById(Long id) {
        ResponseEntity responseEntity;
        Post post = postRepository.findById(id).orElseThrow();
        User currentSecurityUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String postUser = post.getUser().getEmail();
        String currentUser= currentSecurityUser.getEmail();

        if(currentUser.equals(postUser)) {
            postRepository.delete(post);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return responseEntity;
    }
}
