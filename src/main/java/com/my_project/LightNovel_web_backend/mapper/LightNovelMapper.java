package com.my_project.LightNovel_web_backend.mapper;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.dto.response.LigthNovelResponse;
import com.my_project.LightNovel_web_backend.entity.Genre;
import com.my_project.LightNovel_web_backend.entity.LightNovel;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class LightNovelMapper {

    public LightNovel requestToEntity(LigthNovelRequest request) {
        LightNovel lightNovel = new LightNovel();
        lightNovel.setName(request.getName());
        lightNovel.setDisplayName(request.getDisplayName());
        lightNovel.setAuthor(request.getAuthor());
        lightNovel.setImage(request.getImage());
        lightNovel.setDescription(request.getDescription());
        lightNovel.setStatus(request.getStatus());

        return lightNovel;
    }
    public LigthNovelResponse entityToResponse(LightNovel lightNovel) {
        List<String> genres = lightNovel.getGenres().stream().map(Genre::getName).toList();
        return LigthNovelResponse.builder()
                .id(lightNovel.getId())
                .author(lightNovel.getAuthor())
                .image(lightNovel.getImage())
                .genres(genres)
                .name(lightNovel.getName())
                .translationGroups(lightNovel.getTranslationGroups())
                .description(lightNovel.getDescription())
                .status(lightNovel.getStatus())
                .build();
    }
}
