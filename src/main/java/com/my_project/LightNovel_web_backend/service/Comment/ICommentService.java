package com.my_project.LightNovel_web_backend.service.Comment;

import com.my_project.LightNovel_web_backend.dto.request.CommentRequest;
import com.my_project.LightNovel_web_backend.dto.response.CommentResponse;

import java.util.List;

public interface ICommentService {
    CommentResponse addComment(String username, CommentRequest request, long chapterId);
    List<CommentResponse> findAllByChapterId(long chapterId);
//    CommentResponse editComment(long commentId, CommentRequest request);
    void deleteComment(long commentId);

}
