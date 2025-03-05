package com.my_project.LightNovel_web_backend.service.LightNovel;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.dto.response.LigthNovelResponse;
import com.my_project.LightNovel_web_backend.entity.LightNovel;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.mapper.LigthNovelMapper;
import com.my_project.LightNovel_web_backend.repository.LigthNovelRepository;
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

    private final LigthNovelRepository ligthNovelRepository;
    private final LigthNovelMapper ligthNovelMapper;

    @Override
    @Transactional
    public LigthNovelResponse addNew(LigthNovelRequest request) {
        LightNovel lightNovel = ligthNovelMapper.requestToEntity(request);
        lightNovel = ligthNovelRepository.save(lightNovel);
        return ligthNovelMapper.entityToResponse(lightNovel);
    }

    @Override
    public List<LigthNovelResponse> findAll(Pageable pageable) {
        Page<LightNovel> page = ligthNovelRepository.findAll(pageable);

        return page.get().map(ligthNovelMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public int totalPage(int PageSize) {
        return (int)ligthNovelRepository.count()/PageSize;
    }

    @Override
    public List<LigthNovelResponse> findByGeners(List<String> genres, Pageable pageable) {
        Page<LightNovel> ligthNovels = ligthNovelRepository.findByGeners(genres, pageable);

        return ligthNovels.get().map(ligthNovelMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<LigthNovelResponse> findByLatestChapterUpdate(Pageable pageable) {
        Page<LightNovel> ligthNovels = ligthNovelRepository.findByLatestChapterUpdate(pageable);

        return ligthNovels.get().map(ligthNovelMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<LigthNovelResponse> findByGenreSortByLastestChapterUpdate(List<String> genres, Pageable pageable) {
        Page<LightNovel> ligthNovels = ligthNovelRepository.findByGenersSortByLastestChapterUpdate(genres, pageable);
        return ligthNovels.get().map(ligthNovelMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean deleteLigthNovel(long id) {
        if (!ligthNovelRepository.existsById(id)) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        try {
            ligthNovelRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public LigthNovelResponse getById(long id) {
        Optional<LightNovel> ligthNovel = ligthNovelRepository.findById(id);
        if (ligthNovel.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }

        return ligthNovelMapper.entityToResponse(ligthNovel.get());
    }

    @Override
    @Transactional
    public LigthNovelResponse editLigthNovel(long id, LigthNovelRequest request) {
        if (!ligthNovelRepository.existsById(id)) {
            throw new AppException(ErrorCode.INVALID_REQUEST);
        }
        LightNovel lightNovel = ligthNovelMapper.requestToEntity(request);
        lightNovel.setId(id);
        ligthNovelRepository.save(lightNovel);

        return ligthNovelMapper.entityToResponse(lightNovel);
    }
}
