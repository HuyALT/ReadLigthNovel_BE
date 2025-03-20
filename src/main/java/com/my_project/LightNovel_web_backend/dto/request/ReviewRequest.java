package com.my_project.LightNovel_web_backend.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewRequest {
    private String content;

    private int rating;
}
