package com.kuzin.feedservice.redis.repository;

import com.kuzin.feedservice.entity.Post;
import com.kuzin.feedservice.redis.entity.RedisPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("redisPostRepository")
public class RedisPostRepository {

    @Autowired
    private RedisTemplate redisTemplate;

    public RedisPost save(Post post) {
        System.out.println(post.toString());
        RedisPost redisPost = RedisPost.builder()
                .id(post.getId())
                .base64Image(post.getBase64Image())
                .text(post.getText())
                .userId(post.getUser().getId())
                .build();
        redisTemplate.opsForHash().put("Post", redisPost.getId(), redisPost);
        return redisPost;
    }

    public List<RedisPost> findAll() {
        return redisTemplate.opsForHash().values("Post");
    }

    public RedisPost findPostById(Long id) {
        System.out.println("called findPostById from db");
        return (RedisPost) redisTemplate.opsForHash().get("Post",id);
    }

    public void deletePost(Long id) {
        redisTemplate.opsForHash().delete("Post",id);
    }
}
