package com.my_project.LightNovel_web_backend.mapper;

import com.my_project.LightNovel_web_backend.dto.request.GenreRequest;
import com.my_project.LightNovel_web_backend.dto.response.GenreReponse;
import com.my_project.LightNovel_web_backend.entity.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public Genre requestToEntiry (GenreRequest request) {
        Genre genre = new Genre();
        genre.setName(request.getName());
        genre.setDescription(request.getDescription());
        return genre;
    }

    public GenreReponse entiryToResponse(Genre genre) {
        return GenreReponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .description(genre.getDescription())
                .build();
    }
}
