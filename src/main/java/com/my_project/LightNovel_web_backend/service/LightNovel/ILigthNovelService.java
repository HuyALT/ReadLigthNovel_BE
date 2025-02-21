package com.my_project.LightNovel_web_backend.service.LightNovel;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.dto.response.LigthNovelResponse;
import com.my_project.LightNovel_web_backend.entity.LigthNovel;

import java.util.List;

public interface ILigthNovelService {
    LigthNovelResponse addNew(LigthNovelRequest request);
    List<LigthNovelResponse> findAll(int pageNo, int pageSize, String sortBy);
    int totalPage(int PageSize);

}
