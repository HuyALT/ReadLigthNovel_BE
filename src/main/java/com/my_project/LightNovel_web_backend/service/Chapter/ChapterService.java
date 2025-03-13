package com.my_project.LightNovel_web_backend.service.Chapter;

import com.my_project.LightNovel_web_backend.dto.request.ChapterRequest;
import com.my_project.LightNovel_web_backend.dto.response.ChapterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterService implements IChapterService {
    @Override
    public ChapterResponse addNew(ChapterRequest request) {
        return null;
    }

    @Override
    public ChapterResponse updateChapter(ChapterRequest request) {
        return null;
    }

    @Override
    public void deleteChapter(long chapterId) {

    }

    @Override
    public void increaseView(long chapterId) {

    }

    @Override
    public List<ChapterResponse> getChaptersByLightNovel(long lightnovelId) {
        return List.of();
    }
}
