package com.my_project.LightNovel_web_backend.service.LightNovel;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.dto.response.LigthNovelResponse;
import com.my_project.LightNovel_web_backend.entity.Genre;
import com.my_project.LightNovel_web_backend.entity.LightNovel;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.mapper.LightNovelMapper;
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

    private final LightNovelRepository ligthNovelRepository;
    private final LightNovelMapper lightNovelMapper;

    @Override
    @Transactional
    public LigthNovelResponse addNew(LigthNovelRequest request) {
        LightNovel lightNovel = lightNovelMapper.requestToEntity(request);
        lightNovel = ligthNovelRepository.save(lightNovel);
        return lightNovelMapper.entityToResponse(lightNovel);
    }

    @Override
    public List<LigthNovelResponse> findAll(Pageable pageable) {
        Page<LightNovel> lightNovels = ligthNovelRepository.findAll(pageable);
        if (lightNovels.isEmpty()) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        return lightNovels.get().map(lightNovelMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public int totalPage(int PageSize) {
        return (int)ligthNovelRepository.count()/PageSize;
    }

    @Override
    public List<LigthNovelResponse> findByGeners(List<String> genres, Pageable pageable) {
        Page<LightNovel> lightNovels = ligthNovelRepository.findByGeners(genres, pageable);
        if (lightNovels.isEmpty()) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        return lightNovels.get().map(lightNovelMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<LigthNovelResponse> findByLatestChapterUpdate(Pageable pageable) {
        Page<LightNovel> lightNovels = ligthNovelRepository.findByLatestChapterUpdate(pageable);
        if (lightNovels.isEmpty()) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        return lightNovels.get().map(lightNovelMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<LigthNovelResponse> findByGenreSortByLastestChapterUpdate(List<String> genres, Pageable pageable) {
        Page<LightNovel> lightNovels = ligthNovelRepository.findByGenersSortByLastestChapterUpdate(genres, pageable);
        if (lightNovels.isEmpty()) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        return lightNovels.get().map(lightNovelMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean deleteLigthNovel(long id) {
        Optional<LightNovel> lightNovel = ligthNovelRepository.findById(id);
        if (lightNovel.isEmpty()) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        try {
            for (Genre genre : lightNovel.get().getGenres()){
                genre.getLightNovels().remove(lightNovel.get());
            }
            lightNovel.get().getGenres().clear();
            ligthNovelRepository.delete(lightNovel.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public LigthNovelResponse getById(long id) {
        Optional<LightNovel> ligthNovel = ligthNovelRepository.findById(id);
        if (ligthNovel.isEmpty()) {
            throw new AppException(ErrorCode.NOTFOUND);
        }

        return lightNovelMapper.entityToResponse(ligthNovel.get());
    }

    @Override
    @Transactional
    public LigthNovelResponse editLigthNovel(long id, LigthNovelRequest request) {
        if (!ligthNovelRepository.existsById(id)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        LightNovel lightNovel = lightNovelMapper.requestToEntity(request);
        lightNovel.setId(id);
        ligthNovelRepository.save(lightNovel);

        return lightNovelMapper.entityToResponse(lightNovel);
    }
}
