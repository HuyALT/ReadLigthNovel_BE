package com.my_project.LightNovel_web_backend.service.Genre;

import com.my_project.LightNovel_web_backend.dto.request.GenreRequest;
import com.my_project.LightNovel_web_backend.dto.response.GenreReponse;
import com.my_project.LightNovel_web_backend.entity.Genre;
import com.my_project.LightNovel_web_backend.entity.LightNovel;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.mapper.GenreMapper;
import com.my_project.LightNovel_web_backend.repository.GenreRepository;
import com.my_project.LightNovel_web_backend.repository.LightNovelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService implements IGenreService {

    private final GenreRepository genreRepository;
    private final LightNovelRepository ligthNovelRepository;
    private final GenreMapper genreMapper;

    @Override
    @Transactional
    public GenreReponse addNew(GenreRequest request) {
        Genre genre = genreMapper.requestToEntiry(request);
        return genreMapper.entiryToResponse(genreRepository.save(genre));
    }

    @Override
    public void deleteGenre(int id) {
        Genre genre = genreRepository.findById(id).orElseThrow(
                ()-> new AppException(ErrorCode.INVALID_REQUEST)
        );
        for (LightNovel lightNovel : genre.getLightNovels()){
            lightNovel.getGenres().remove(genre);
        }

        genre.getLightNovels().clear();
        genreRepository.delete(genre);
    }

    @Override
    public List<GenreReponse> findAll() {

        return genreRepository.findAll().stream().map(genreMapper::entiryToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GenreReponse editGenre(GenreRequest request, int id) {
        Genre genre = genreRepository.findById(id).orElseThrow(
                ()-> new AppException(ErrorCode.INVALID_REQUEST)
        );
        genre.setName(request.getName());
        genre.setDescription(request.getDescription());
        genre.setId(id);
        return genreMapper.entiryToResponse(genreRepository.save(genre));
    }
}
