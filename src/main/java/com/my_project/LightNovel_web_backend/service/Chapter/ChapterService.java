package com.my_project.LightNovel_web_backend.service.Chapter;

import com.my_project.LightNovel_web_backend.dto.request.ChapterRequest;
import com.my_project.LightNovel_web_backend.dto.response.ChapterResponse;
import com.my_project.LightNovel_web_backend.entity.Chapter;
import com.my_project.LightNovel_web_backend.entity.LightNovel;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.mapper.ChapterMapper;
import com.my_project.LightNovel_web_backend.repository.ChapterRepository;
import com.my_project.LightNovel_web_backend.repository.LightNovelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChapterService implements IChapterService {

    private final ChapterRepository chapterRepository;
    private final LightNovelRepository lightNovelRepository;
    private final ChapterMapper chapterMapper;

    @Override
    @Transactional
    public ChapterResponse addNew(ChapterRequest request) {
        LightNovel lightNovel = lightNovelRepository.findById(request.getLightNovelId()).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST)
        );
        Chapter chapter = chapterMapper.requestToEntity(request);
        chapter.setLightNovel(lightNovel);
        chapter.setViewtotal(0);

        return chapterMapper.entityToResponse(chapterRepository.save(chapter));
    }

    @Override
    @Transactional
    public ChapterResponse updateChapter(ChapterRequest request, long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST)
        );
        chapter.setViewtotal(0);
        return chapterMapper.entityToResponse(chapter);
    }

    @Override
    @Transactional
    public void deleteChapter(long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST)
        );
        chapterRepository.delete(chapter);
    }

    @Override
    public void increaseView(long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST)
        );
        chapter.setViewtotal(chapter.getViewtotal()+1);
        chapterRepository.save(chapter);
    }

    @Override
    public List<ChapterResponse> getChaptersByLightNovel(long lightnovelId) {
        LightNovel lightNovel = lightNovelRepository.findById(lightnovelId).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST)
        );
        return lightNovel.getChapters().stream().map(chapterMapper::entityToResponse).collect(Collectors.toList());
    }
}
