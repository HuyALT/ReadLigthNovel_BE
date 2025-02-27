package com.my_project.LightNovel_web_backend.dto.request;

import com.my_project.LightNovel_web_backend.Enum.LigthNovelStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    private List<Long> genreId;
}
