package com.my_project.LightNovel_web_backend.controller.admin;

import com.my_project.LightNovel_web_backend.dto.request.ChapterRequest;
import com.my_project.LightNovel_web_backend.enums.ChapterStatus;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.service.Chapter.ChapterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/chapter")
public class ChapterAdminController {

    private final ChapterService chapterService;

    @PostMapping("/add")
    public ResponseEntity<?> addChapter(@RequestBody @Valid ChapterRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new AppException(ErrorCode.INVALID_REQUEST, request);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(chapterService.addNew(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChapter(@RequestBody @Valid ChapterRequest request,
                                           @PathVariable Long chapterId,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new AppException(ErrorCode.INVALID_REQUEST, request);
        }
        return ResponseEntity.status(HttpStatus.OK).body(chapterService.updateChapter(request,chapterId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChapter(@PathVariable Long chapterId) {
        chapterService.deleteChapter(chapterId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable long id,
                                          @RequestParam ChapterStatus status) {
        chapterService.changeStatus(id, status);
        return ResponseEntity.ok().build();
    }

}
