package com.my_project.LightNovel_web_backend.dto.request;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private String content;
}
