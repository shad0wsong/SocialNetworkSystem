package com.kuzin.frontservice.feign;

import com.kuzin.frontservice.entity.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "feign", url = "http://localhost:8001/post")
public interface FeignService {

    @GetMapping("/currentUser")
     List<Post> getCurrentUserPosts();
}
