package com.kuzin.feedservice.dto;

import com.kuzin.feedservice.entity.HashTag;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostCreateRequest {

    String text;

    String base64Image;

    List<String> hashTagList;
}
