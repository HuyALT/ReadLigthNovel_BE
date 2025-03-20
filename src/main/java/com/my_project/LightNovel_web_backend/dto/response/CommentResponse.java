package com.my_project.LightNovel_web_backend.dto.response;

import lombok.*;


import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private long id;
    private String content;

    private LocalDateTime updateAt;

    private UserResponse user;

    private Long chapterId;
}
