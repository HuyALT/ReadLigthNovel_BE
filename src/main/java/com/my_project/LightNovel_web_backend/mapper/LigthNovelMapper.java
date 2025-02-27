package com.my_project.LightNovel_web_backend.mapper;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.dto.response.LigthNovelResponse;
import com.my_project.LightNovel_web_backend.entity.Genre;
import com.my_project.LightNovel_web_backend.entity.LigthNovel;
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

    public LigthNovel requestToEntity(LigthNovelRequest request) {
        LigthNovel ligthNovel = new LigthNovel();
        ligthNovel.setName(request.getName());
        ligthNovel.setDisplayName(request.getDisplayName());
        ligthNovel.setAuthor(request.getAuthor());
        ligthNovel.setImage(request.getImage());
        ligthNovel.setDescription(request.getDescription());
        ligthNovel.setStatus(request.getStatus());
        for (Long index: request.getGenreId()) {
            Optional<Genre> genre = genreRepository.findById(index);
            if (genre.isEmpty()) {
                throw new AppException(ErrorCode.INVALID_REQUEST);
            }
            ligthNovel.getGenres().add(genre.get());
        }
        return ligthNovel;
    }
    public LigthNovelResponse entityToResponse(LigthNovel ligthNovel) {
        LigthNovelResponse ligthNovelResponse = new LigthNovelResponse();
        ligthNovelResponse.setAuthor(ligthNovel.getAuthor());
        ligthNovelResponse.setId(ligthNovel.getId());
        ligthNovelResponse.setStatus(ligthNovel.getStatus());
        ligthNovelResponse.setImage(ligthNovel.getImage());
        ligthNovelResponse.setName(ligthNovel.getName());
        ligthNovelResponse.setDescription(ligthNovel.getDescription());
        ligthNovelResponse.setTranslationGroups(ligthNovel.getTranslationGroups());
        return ligthNovelResponse;
    }
}
