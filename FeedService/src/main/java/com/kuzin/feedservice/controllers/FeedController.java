package com.kuzin.feedservice.controllers;

import com.kuzin.feedservice.dto.PostCreateRequest;
import com.kuzin.feedservice.dto.PostEditRequest;
import com.kuzin.feedservice.entity.Post;
import com.kuzin.feedservice.logic.PostLogic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final PostLogic postLogic;

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

    @PostMapping("/post/edit")
    public ResponseEntity editPostById(@RequestBody PostEditRequest postEditRequest) {
        return postLogic.editPost(postEditRequest);
    }

    @PostMapping("/post/{id}/delete")
    public ResponseEntity editPostById(@PathVariable (value = "id") Long id) {
        return postLogic.deletePostById(id);
    }

}
