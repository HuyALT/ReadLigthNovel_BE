package com.my_project.LightNovel_web_backend.controller;

import com.my_project.LightNovel_web_backend.service.Chapter.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chapter")
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping
    public ResponseEntity<?> getbyLightNovel(@RequestParam long lightNovelId) {

        return ResponseEntity.status(HttpStatus.OK).body(chapterService.getChaptersByLightNovel(lightNovelId));
    }

}

