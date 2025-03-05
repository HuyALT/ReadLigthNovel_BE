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

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class LigthNovelMapper {

    private final GenreRepository genreRepository;

    public LightNovel requestToEntity(LigthNovelRequest request) {
        LightNovel lightNovel = new LightNovel();
        lightNovel.setName(request.getName());
        lightNovel.setDisplayName(request.getDisplayName());
        lightNovel.setAuthor(request.getAuthor());
        lightNovel.setImage(request.getImage());
        lightNovel.setDescription(request.getDescription());
        lightNovel.setStatus(request.getStatus());
        for (int index: request.getGenreId()) {
            Optional<Genre> genre = genreRepository.findById(index);
            if (genre.isEmpty()) {
                throw new AppException(ErrorCode.INVALID_REQUEST);
            }
            lightNovel.getGenres().add(genre.get());
        }
        return lightNovel;
    }
    public LigthNovelResponse entityToResponse(LightNovel lightNovel) {
        LigthNovelResponse ligthNovelResponse = new LigthNovelResponse();
        ligthNovelResponse.setAuthor(lightNovel.getAuthor());
        ligthNovelResponse.setId(lightNovel.getId());
        ligthNovelResponse.setStatus(lightNovel.getStatus());
        ligthNovelResponse.setImage(lightNovel.getImage());
        ligthNovelResponse.setName(lightNovel.getName());
        ligthNovelResponse.setDescription(lightNovel.getDescription());
        ligthNovelResponse.setTranslationGroups(lightNovel.getTranslationGroups());
        return ligthNovelResponse;
    }
}
