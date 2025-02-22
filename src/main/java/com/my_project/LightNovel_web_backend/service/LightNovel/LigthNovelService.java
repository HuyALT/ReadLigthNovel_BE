package com.my_project.LightNovel_web_backend.service.LightNovel;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.dto.response.LigthNovelResponse;
import com.my_project.LightNovel_web_backend.entity.LigthNovel;
import com.my_project.LightNovel_web_backend.mapper.LigthNovelMapper;
import com.my_project.LightNovel_web_backend.repository.LigthNovelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LigthNovelService implements ILigthNovelService {

    private final LigthNovelRepository ligthNovelRepository;
    private final LigthNovelMapper ligthNovelMapper;

    @Override
    @Transactional
    public LigthNovelResponse addNew(LigthNovelRequest request) {
        LigthNovel ligthNovel = ligthNovelMapper.requestToEntity(request);
        ligthNovel = ligthNovelRepository.save(ligthNovel);
        return ligthNovelMapper.entityToResponse(ligthNovel);
    }

    @Override
    public List<LigthNovelResponse> findAll(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<LigthNovel> page = ligthNovelRepository.findAll(pageable);

        return page.getContent().stream().map(ligthNovelMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public int totalPage(int PageSize) {
        return (int)ligthNovelRepository.count()/PageSize;
    }

    @Override
    public List<LigthNovelResponse> findByGeners(List<String> genres) {
        return List.of();
    }

    @Override
    public List<LigthNovelResponse> findByLatestChapterUpdate(Pageable pageable) {
        return List.of();
    }
}
