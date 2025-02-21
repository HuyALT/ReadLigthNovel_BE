package com.my_project.LightNovel_web_backend.dto.response;

import com.my_project.LightNovel_web_backend.Enum.LigthNovelStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LigthNovelResponse {

    private long id;

    private String name;

    private String image;

    private String description;

    private String author;

    private LigthNovelStatus status;

    private String translationGroups;
}
