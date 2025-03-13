package com.my_project.LightNovel_web_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChapterRequest {

        @NotBlank
        private String title;

        @NotBlank
        @Positive
        private float chapter_number;

        @NotBlank
        private String content;

        @NotBlank
        private long lightNovelId;
}
