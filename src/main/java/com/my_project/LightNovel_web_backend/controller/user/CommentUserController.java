package com.my_project.LightNovel_web_backend.controller.user;

import com.my_project.LightNovel_web_backend.dto.request.CommentRequest;
import com.my_project.LightNovel_web_backend.service.Comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/comment")
public class CommentUserController {

    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@AuthenticationPrincipal Jwt jwt,
                                        @RequestBody CommentRequest request,
                                        @RequestParam long chapterId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.addComment(jwt.getSubject(), request, chapterId));
    }
}
