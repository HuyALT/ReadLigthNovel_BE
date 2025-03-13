package com.my_project.LightNovel_web_backend.service.Chapter;

import com.my_project.LightNovel_web_backend.dto.request.ChapterRequest;
import com.my_project.LightNovel_web_backend.dto.response.ChapterResponse;

import java.util.List;

public interface IChapterService {

    ChapterResponse addNew(ChapterRequest request);
    ChapterResponse updateChapter(ChapterRequest request);
    void deleteChapter(long chapterId);

    void increaseView(long chapterId);
    List<ChapterResponse> getChaptersByLightNovel(long lightnovelId);

}
