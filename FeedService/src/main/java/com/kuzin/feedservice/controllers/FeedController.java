package com.kuzin.feedservice.controllers;

import com.kuzin.feedservice.dto.PostCreateRequest;
import com.kuzin.feedservice.dto.PostEditRequest;
import com.kuzin.feedservice.entity.Post;
import com.kuzin.feedservice.logic.PostLogic;
import com.kuzin.feedservice.redis.entity.RedisPost;
import com.kuzin.feedservice.redis.repository.RedisPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@EnableCaching
public class FeedController {

    private final PostLogic postLogic;

    private final RedisPostRepository redisPostRepository;

    @PostMapping("/post/create")
    public void createPost(@RequestBody PostCreateRequest request) {
        postLogic.createPost(request);
    }

    @GetMapping("/post/{email}")
    public List<Post> getPostByEmail(@PathVariable(value="email") String email) {
        return postLogic.getPostsByUserEmail(email);
    }

    @GetMapping("/post/currentUser")
    public List<Post> getCurrentUserPosts() {
        System.out.println("current post  user info");
        return postLogic.getCurrentUserPosts();
    }

    @GetMapping("/post")
    public List<Post> getAllPosts() {
        return postLogic.getAllPosts();
    }

    @GetMapping("/post/redis")
    public List<RedisPost> getAllRedisPosts() {
        return redisPostRepository.findAll();
    }

    @PostMapping("/redis/{id}/delete")
    public void deleteRedisPost(@PathVariable (value = "id") Long id) {
        redisPostRepository.deletePost(id);
    }

    @GetMapping("/redis/{id}")
    @Cacheable(key = "#id",value = "Post")
    public RedisPost getPostById(@PathVariable (value = "id") Long id) {
         return redisPostRepository.findPostById(id);
    }

    @PostMapping("/post/edit")
    public ResponseEntity editPostById(@RequestBody PostEditRequest postEditRequest) {
        return postLogic.editPost(postEditRequest);
    }

    @PostMapping("/post/{id}/delete")
    public ResponseEntity editPostById(@PathVariable (value = "id") Long id) {
        return postLogic.deletePostById(id);
    }

}
