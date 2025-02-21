package com.my_project.LightNovel_web_backend.mapper;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.dto.response.LigthNovelResponse;
import com.my_project.LightNovel_web_backend.entity.LigthNovel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LigthNovelMapper {
    public LigthNovel requestToEntity(LigthNovelRequest request);
    public LigthNovelResponse entityToResponse(LigthNovel ligthNovel);
}
