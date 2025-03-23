package com.my_project.LightNovel_web_backend.service.Chapter;

import com.my_project.LightNovel_web_backend.dto.request.ChapterRequest;
import com.my_project.LightNovel_web_backend.dto.response.ChapterResponse;
import com.my_project.LightNovel_web_backend.enums.ChapterStatus;

import java.util.List;
import java.util.UUID;

public interface IChapterService {

    ChapterResponse addNew(ChapterRequest request);
    ChapterResponse updateChapter(ChapterRequest request, long chapterId);
    void deleteChapter(long chapterId);

    void increaseView(long chapterId, String ipAddress);
    List<ChapterResponse> findChaptersByLightNovel(long lightnovelId);
    void changeStatus(long chapterId, ChapterStatus status);

}
