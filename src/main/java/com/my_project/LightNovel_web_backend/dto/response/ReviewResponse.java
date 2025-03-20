package com.my_project.LightNovel_web_backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private long id;

    private String content;

    private int rating;

    private UserResponse userResponse;

    private long lightNovelId;

    private LocalDateTime updateAt;
}
