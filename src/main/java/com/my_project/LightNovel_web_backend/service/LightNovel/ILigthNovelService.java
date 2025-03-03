package com.my_project.LightNovel_web_backend.service.LightNovel;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.dto.response.LigthNovelResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ILigthNovelService {

    List<LigthNovelResponse> findAll(Pageable pageable);
    int totalPage(int PageSize);
    List<LigthNovelResponse> findByGeners(List<String> genres, Pageable pageable);
    List<LigthNovelResponse> findByLatestChapterUpdate(Pageable pageable);
    List<LigthNovelResponse> findByGenreSortByLastestChapterUpdate(List<String> genres ,Pageable pageable);
    LigthNovelResponse getById(long id);

    LigthNovelResponse addNew(LigthNovelRequest request);
    boolean deleteLigthNovel(long id);
    LigthNovelResponse editLigthNovel(long id, LigthNovelRequest request);
}
