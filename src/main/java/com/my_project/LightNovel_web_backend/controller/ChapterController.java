package com.my_project.LightNovel_web_backend.controller;

import com.my_project.LightNovel_web_backend.service.Chapter.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chapter")
public class ChapterController {

    private final ChapterService chapterService;

    @GetMapping("/{lightNovelId}")
    public ResponseEntity<?> getbyLightNovel(@PathVariable long lightNovelId) {

        return ResponseEntity.status(HttpStatus.OK).body(chapterService.findChaptersByLightNovel(lightNovelId));
    }

    @PostMapping("/{lightNovelId}")
    public ResponseEntity<?> incrView(@PathVariable long lightNovelId,
                                      @RequestParam String ipAddress) {
        chapterService.increaseView(lightNovelId, ipAddress);
        return ResponseEntity.ok().build();
    }

}

