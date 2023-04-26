package com.kuzin.feedservice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kuzin.feedservice.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class PostEditRequest {
    Long id;

    String text;

    String base64Image;
}
