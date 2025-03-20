package com.my_project.LightNovel_web_backend.mapper;

import com.my_project.LightNovel_web_backend.dto.request.CommentRequest;
import com.my_project.LightNovel_web_backend.dto.response.CommentResponse;
import com.my_project.LightNovel_web_backend.dto.response.UserResponse;
import com.my_project.LightNovel_web_backend.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment requestToEntity(CommentRequest request) {
        Comment comment = new Comment();
        comment.setContent(request.getContent());
        return comment;
    }

    public CommentResponse entityToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .updateAt(comment.getUpdateAt())
                .chapterId(comment.getChapter().getId())
                .user(UserResponse.builder()
                        .userName(comment.getUser().getUserName())
                        .image(comment.getUser().getImage())
                        .build()
                )
                .build();
    }
}
