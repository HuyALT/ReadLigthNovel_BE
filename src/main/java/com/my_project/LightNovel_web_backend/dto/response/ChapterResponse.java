package com.my_project.LightNovel_web_backend.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChapterResponse {

    public class Chapter {

        private long id;

        private String title;

        private float chapter_number;

        private String content;

        private LocalDateTime createAt;

        private LocalDateTime updateAt;

        private int viewtotal;
    }
}
