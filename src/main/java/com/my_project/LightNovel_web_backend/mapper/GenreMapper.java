package com.my_project.LightNovel_web_backend.mapper;

import com.my_project.LightNovel_web_backend.dto.request.GenreRequest;
import com.my_project.LightNovel_web_backend.dto.response.GenreReponse;
import com.my_project.LightNovel_web_backend.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    Genre requestToEntiry (GenreRequest request);

    GenreReponse entiryToResponse(Genre genre);
}
