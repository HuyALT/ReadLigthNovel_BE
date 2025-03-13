package com.my_project.LightNovel_web_backend.mapper;

import com.my_project.LightNovel_web_backend.dto.request.ChapterRequest;
import com.my_project.LightNovel_web_backend.dto.response.ChapterResponse;
import com.my_project.LightNovel_web_backend.entity.Chapter;
import com.my_project.LightNovel_web_backend.repository.LightNovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class ChapterMapper {

    public ChapterResponse entityToResponse(Chapter chapter) {
        return ChapterResponse.builder()
                .id(chapter.getId())
                .chapter_number(chapter.getChapter_number())
                .title(chapter.getTitle())
                .content(chapter.getContent())
                .updateAt(chapter.getUpdateAt())
                .createAt(chapter.getCreateAt())
                .viewtotal(chapter.getViewtotal())
                .build();
    }

    public Chapter requestToEntity(ChapterRequest request) {
        Chapter chapter = new Chapter();
        chapter.setTitle(request.getTitle());
        chapter.setContent(request.getContent());
        chapter.setChapter_number(request.getChapter_number());
        return chapter;
    }
}
