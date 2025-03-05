package com.my_project.LightNovel_web_backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GenreRequest {

    @NotBlank
    private String name;

    private String description;
}
