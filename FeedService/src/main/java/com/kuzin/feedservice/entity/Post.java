package com.kuzin.feedservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue
    Long id;

    String text;

    @ManyToOne(cascade= CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonBackReference
    User user;

    String base64Image;

}
