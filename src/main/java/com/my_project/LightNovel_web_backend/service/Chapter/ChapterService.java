package com.my_project.LightNovel_web_backend.service.Chapter;

import com.my_project.LightNovel_web_backend.dto.request.ChapterRequest;
import com.my_project.LightNovel_web_backend.dto.response.ChapterResponse;
import com.my_project.LightNovel_web_backend.entity.Chapter;
import com.my_project.LightNovel_web_backend.entity.LightNovel;
import com.my_project.LightNovel_web_backend.enums.ChapterStatus;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.mapper.ChapterMapper;
import com.my_project.LightNovel_web_backend.repository.ChapterRepository;
import com.my_project.LightNovel_web_backend.repository.LightNovelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChapterService implements IChapterService {

    private final ChapterRepository chapterRepository;
    private final LightNovelRepository lightNovelRepository;
    private final ChapterMapper chapterMapper;
    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    @Transactional
    public ChapterResponse addNew(ChapterRequest request) {
        LightNovel lightNovel = lightNovelRepository.findById(request.getLightNovelId()).orElseThrow(
                ()->new AppException(ErrorCode.NOT_FOUND, request)
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
                ()->new AppException(ErrorCode.NOT_FOUND, request)
        );
        chapter.setViewtotal(0);
        return chapterMapper.entityToResponse(chapter);
    }

    @Override
    @Transactional
    public void deleteChapter(long chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                ()->new AppException(ErrorCode.NOT_FOUND, chapterId)
        );
        chapterRepository.delete(chapter);
    }

    @Override
    public void increaseView(long chapterId, String ipAddress) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                ()->new AppException(ErrorCode.NOT_FOUND, chapterId)
        );

        String makeKey = "ip:"+ipAddress +"-"+"chapter:"+chapterId;
        if (redisTemplate.opsForValue().get(makeKey)==null) {
            redisTemplate.opsForValue().set(makeKey, "hits", 30, TimeUnit.MINUTES);

            chapter.setViewtotal(chapter.getViewtotal()+1);
            chapterRepository.save(chapter);
        }
        throw new AppException(ErrorCode.TOO_MANNY_REQUEST, ipAddress);
    }

    @Override
    public List<ChapterResponse> findChaptersByLightNovel(long lightnovelId) {
        LightNovel lightNovel = lightNovelRepository.findById(lightnovelId).orElseThrow(
                ()->new AppException(ErrorCode.NOT_FOUND, lightnovelId)
        );
        return lightNovel.getChapters().stream().map(chapterMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changeStatus(long chapterId, ChapterStatus status) {
        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                ()->new AppException(ErrorCode.NOT_FOUND, chapterId)
        );
        chapter.setChapterStatus(status);
        chapterRepository.save(chapter);
    }
}
