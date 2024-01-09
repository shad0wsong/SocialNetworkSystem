package com.kuzin.feedservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

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

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "post_hashtags",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "hashTagId")
    )
    List<HashTag> hashTagList;

    String base64Image;

    Long views;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", user=" +
                ", base64Image='" + base64Image + '\'' +
                '}';
    }

    public Post(String text, User user, String base64Image) {
        this.text = text;
        this.user = user;
        this.base64Image = base64Image;
    }
}
