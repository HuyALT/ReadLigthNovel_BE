package com.my_project.LightNovel_web_backend.dto.request;

import com.my_project.LightNovel_web_backend.enums.LigthNovelStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LigthNovelRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String displayName;

    private String image;

    private String description;

    private String author;

    @NotBlank
    private LigthNovelStatus status;

    private String translationGroups;

    private List<Integer> genreId;
}
