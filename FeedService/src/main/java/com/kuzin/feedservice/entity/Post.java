package com.kuzin.feedservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Post  implements Serializable {
    @Id
    @GeneratedValue
    Long id;

    String text;

    @ManyToOne(cascade= CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonBackReference
    User user;

    String base64Image;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", user=" +
                ", base64Image='" + base64Image + '\'' +
                '}';
    }
}
