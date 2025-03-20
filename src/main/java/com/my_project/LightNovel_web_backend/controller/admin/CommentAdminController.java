package com.my_project.LightNovel_web_backend.controller.admin;

import com.my_project.LightNovel_web_backend.service.Comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/comment")
public class CommentAdminController {

    private final CommentService commentService;

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteById(@PathVariable long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
