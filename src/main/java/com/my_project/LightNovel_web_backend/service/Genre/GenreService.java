package com.my_project.LightNovel_web_backend.service.Genre;

import com.my_project.LightNovel_web_backend.dto.request.GenreRequest;
import com.my_project.LightNovel_web_backend.dto.response.GenreReponse;
import com.my_project.LightNovel_web_backend.entity.Genre;
import com.my_project.LightNovel_web_backend.entity.LigthNovel;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.mapper.GenreMapper;
import com.my_project.LightNovel_web_backend.repository.GenreRepository;
import com.my_project.LightNovel_web_backend.repository.LigthNovelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService implements IGenreService {

    private final GenreRepository genreRepository;
    private final LigthNovelRepository ligthNovelRepository;
    private final GenreMapper genreMapper;

    @Override
    @Transactional
    public GenreReponse addNew(GenreRequest request) {
        Genre genre = genreMapper.requestToEntiry(request);
        return genreMapper.entiryToResponse(genreRepository.save(genre));
    }

    @Override
    public void deleteGenre(long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(
                ()-> new AppException(ErrorCode.INVALID_REQUEST)
        );
        for (LigthNovel ligthNovel : genre.getLigthNovels()){
            ligthNovel.getGenres().remove(genre);
        }

        ligthNovelRepository.saveAll(genre.getLigthNovels());
    }

    @Override
    public List<GenreReponse> findAll() {

        return genreRepository.findAll().stream().map(genreMapper::entiryToResponse).toList();
    }

    @Override
    @Transactional
    public GenreReponse edit(GenreRequest request, long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(
                ()-> new AppException(ErrorCode.INVALID_REQUEST)
        );
        genre.setName(request.getName());
        genre.setDescription(request.getDescription());
        return genreMapper.entiryToResponse(genreRepository.save(genre));
    }
}
