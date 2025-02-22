package com.my_project.LightNovel_web_backend.mapper;

import com.my_project.LightNovel_web_backend.dto.request.LigthNovelRequest;
import com.my_project.LightNovel_web_backend.dto.response.LigthNovelResponse;
import com.my_project.LightNovel_web_backend.entity.LigthNovel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LigthNovelMapper {
    LigthNovel requestToEntity(LigthNovelRequest request);
    LigthNovelResponse entityToResponse(LigthNovel ligthNovel);
}
