package com.my_project.LightNovel_web_backend.controller;

import com.my_project.LightNovel_web_backend.service.Comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<?> getAllByChapterId(@RequestParam long chapterId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAllByChapterId(chapterId));
    }
}
