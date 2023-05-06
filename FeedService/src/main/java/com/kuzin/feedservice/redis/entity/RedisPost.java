package com.kuzin.feedservice.redis.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kuzin.feedservice.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Post")
@Builder
public class RedisPost implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    String text;

    String base64Image;
}
