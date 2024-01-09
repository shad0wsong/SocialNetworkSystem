package com.kuzin.feedservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class HashTag {
    @Id
    @GeneratedValue
    Long hashTagId;

    String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "hashTagList")
    List<Post> posts;

    public HashTag(String name, List<Post> posts) {
        this.name = name;
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "HashTag{" +
                "id=" + hashTagId +
                ", name='" + name + '\'' +
                '}';
    }
}
