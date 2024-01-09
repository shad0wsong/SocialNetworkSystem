package com.kuzin.feedservice.controllers;

import com.kuzin.feedservice.dto.HashTagCreateRequest;
import com.kuzin.feedservice.dto.PostCreateRequest;
import com.kuzin.feedservice.dto.PostEditRequest;
import com.kuzin.feedservice.entity.HashTag;
import com.kuzin.feedservice.entity.Post;
import com.kuzin.feedservice.logic.PostLogic;
import com.kuzin.feedservice.redis.entity.RedisPost;
import com.kuzin.feedservice.redis.repository.RedisPostRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@EnableCaching
@EnableHystrix
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

    @GetMapping("/hashtag/{hashtagName}/posts")
    public List<Post> getPostsByHashTag(@PathVariable(value="hashtagName") String hashtagName) {
       return postLogic.getPostsByHashTag(hashtagName);
    }

    @PostMapping("/hashtag/create")
    public void createHashtag(@RequestBody HashTagCreateRequest request) {
        postLogic.createHashTag(request);
    }

    @GetMapping("/post/id/{id}")
    public Post getPostById(@PathVariable(value="id") String id) {
        return postLogic.getPostById(id);
    }

    @GetMapping("/post/top/views")
    public List<Post> getTopPosts() {
        return postLogic.getTopPosts();
    }

    @GetMapping("/post/currentUser")
    public List<Post> getCurrentUserPosts() {
        System.out.println("current post  user info");
        return postLogic.getCurrentUserPosts();
    }

    @GetMapping("/post")
    @HystrixCommand(groupKey = "kuzin", commandKey = "kuzin", fallbackMethod = "allPostsFallback")
    public List<Post> getAllPostgresPosts() {
        return postLogic.getAllPosts();
    }

    public String allPostsFallback() {
        System.out.println("here");
        return "feed service failed";
    }


    @GetMapping("/post/redis")
    public List<RedisPost> getAllRedisPosts() {
        return redisPostRepository.findAll();
    }

    @PostMapping("/redis/{id}/delete")
    public void deleteRedisPost(@PathVariable (value = "id") Long id) {
        redisPostRepository.deletePost(id);
    }

    @GetMapping("/post/allPosts")
    public List<Post> getAllPosts() {
        List<RedisPost> redisPosts = redisPostRepository.findAll();
        List<Post> posts = postLogic.getAllPosts();
        List<Post> transformedPosts = postLogic.redisPostToPost(redisPosts);

        List<Post> result = new ArrayList<>();
        result.addAll(transformedPosts);
        result.addAll(posts);

        return result;
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
