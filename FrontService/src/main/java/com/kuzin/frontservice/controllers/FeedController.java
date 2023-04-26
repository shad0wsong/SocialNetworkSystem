package com.kuzin.frontservice.controllers;

import com.kuzin.frontservice.entity.Post;
import com.kuzin.frontservice.feign.FeignService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class FeedController {

    String jwtToken;

    @Autowired
    FeignService feignService;

    @GetMapping("/current")
    public String hello(@NonNull HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<Post>> postsEntity= restTemplate.exchange("http://localhost:8001/post/currentUser",
                HttpMethod.GET, new HttpEntity<>(createHeaders(request.getHeader("Authorization"))), new ParameterizedTypeReference<List<Post>>() {});
        List<Post> posts = postsEntity.getBody();
        System.out.println(posts);
        return "hello";
    }

    HttpHeaders createHeaders(String header){
        return new HttpHeaders() {{
            set( "Authorization", header );
        }};
    }
}
