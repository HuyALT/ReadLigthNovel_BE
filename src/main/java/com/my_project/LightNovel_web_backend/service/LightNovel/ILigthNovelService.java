package com.my_project.LightNovel_web_backend.service.LightNovel;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.dto.response.LigthNovelResponse;
import com.my_project.LightNovel_web_backend.entity.LigthNovel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ILigthNovelService {
    LigthNovelResponse addNew(LigthNovelRequest request);
    List<LigthNovelResponse> findAll(Pageable pageable);
    int totalPage(int PageSize);
    List<LigthNovelResponse> findByGeners(List<String> genres, Pageable pageable);
    List<LigthNovelResponse> findByLatestChapterUpdate(Pageable pageable);
    List<LigthNovelResponse> findByGenreSortByLastestChapterUpdate(List<String> genres ,Pageable pageable);
    boolean deleteLigthNovel(long id);
}
