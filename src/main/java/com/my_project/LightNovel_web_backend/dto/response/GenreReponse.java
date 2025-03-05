package com.my_project.LightNovel_web_backend.dto.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenreReponse {

    private int id;

    private String name;

    private String description;
}
