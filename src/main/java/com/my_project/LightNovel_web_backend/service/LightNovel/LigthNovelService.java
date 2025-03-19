package com.my_project.LightNovel_web_backend.service.LightNovel;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.dto.response.LigthNovelResponse;
import com.my_project.LightNovel_web_backend.entity.Genre;
import com.my_project.LightNovel_web_backend.entity.LightNovel;
import com.my_project.LightNovel_web_backend.enums.LigthNovelStatus;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.mapper.LightNovelMapper;
import com.my_project.LightNovel_web_backend.repository.GenreRepository;
import com.my_project.LightNovel_web_backend.repository.LightNovelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LigthNovelService implements ILigthNovelService {

    private final LightNovelRepository lightNovelRepository;
    private final LightNovelMapper lightNovelMapper;
    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public LigthNovelResponse addNew(LigthNovelRequest request) {
        LightNovel lightNovel = lightNovelMapper.requestToEntity(request);
        for (int index: request.getGenreId()) {
            Optional<Genre> genre = genreRepository.findById(index);
            if (genre.isEmpty()) {
                throw new AppException(ErrorCode.INVALID_REQUEST, request);
            }
            lightNovel.getGenres().add(genre.get());
        }
        lightNovel.setStatus(LigthNovelStatus.HIDDEN);
        lightNovel = lightNovelRepository.save(lightNovel);
        return lightNovelMapper.entityToResponse(lightNovel);
    }

    @Override
    public List<LigthNovelResponse> findAll(Pageable pageable) {
        Page<LightNovel> lightNovels = lightNovelRepository.findAll(pageable);
        if (lightNovels.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND,null);
        }

        return lightNovels.get().map(lightNovelMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public int totalPage(int PageSize) {
        return (int) lightNovelRepository.count()/PageSize;
    }


    @Override
    public List<LigthNovelResponse> findByLatestChapterUpdate(Pageable pageable) {
        Page<LightNovel> lightNovels = lightNovelRepository.findByLatestChapterUpdate(pageable);
        if (lightNovels.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND, null);
        }
        return lightNovels.get().map(lightNovelMapper::entityToResponse)
                .filter(ligthNovelResponse -> ligthNovelResponse.getStatus()!=LigthNovelStatus.HIDDEN)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigthNovelResponse> findByGenreSortByLastestChapterUpdate(List<String> genres, Pageable pageable) {
        Page<LightNovel> lightNovels = lightNovelRepository.findByGenersSortByLastestChapterUpdate(genres, pageable);
        if (lightNovels.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND, null);
        }
        return lightNovels.get().map(lightNovelMapper::entityToResponse).
                filter(ligthNovelResponse -> ligthNovelResponse.getStatus()!=LigthNovelStatus.HIDDEN)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean deleteLigthNovel(long id) {
        Optional<LightNovel> lightNovel = lightNovelRepository.findById(id);
        if (lightNovel.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND, id);
        }
        try {
            for (Genre genre : lightNovel.get().getGenres()){
                genre.getLightNovels().remove(lightNovel.get());
            }
            lightNovel.get().getGenres().clear();
            lightNovelRepository.delete(lightNovel.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public LigthNovelResponse findById(long id) {
        Optional<LightNovel> ligthNovel = lightNovelRepository.findById(id);
        if (ligthNovel.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND, id);
        }

        return lightNovelMapper.entityToResponse(ligthNovel.get());
    }

    @Override
    @Transactional
    public LigthNovelResponse updateLigthNovel(long id, LigthNovelRequest request) {
        if (!lightNovelRepository.existsById(id)) {
            throw new AppException(ErrorCode.NOT_FOUND, id);
        }
        LightNovel lightNovel = lightNovelMapper.requestToEntity(request);
        lightNovel.setId(id);
        lightNovelRepository.save(lightNovel);

        return lightNovelMapper.entityToResponse(lightNovel);
    }
}
