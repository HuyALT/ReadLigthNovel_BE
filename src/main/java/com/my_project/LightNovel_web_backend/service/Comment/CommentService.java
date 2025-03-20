package com.my_project.LightNovel_web_backend.service.Comment;

import com.my_project.LightNovel_web_backend.dto.request.CommentRequest;
import com.my_project.LightNovel_web_backend.dto.response.CommentResponse;
import com.my_project.LightNovel_web_backend.entity.Chapter;
import com.my_project.LightNovel_web_backend.entity.Comment;
import com.my_project.LightNovel_web_backend.entity.User;
import com.my_project.LightNovel_web_backend.exception.AppException;
import com.my_project.LightNovel_web_backend.exception.ErrorCode;
import com.my_project.LightNovel_web_backend.mapper.CommentMapper;
import com.my_project.LightNovel_web_backend.repository.ChapterRepository;
import com.my_project.LightNovel_web_backend.repository.CommentRepository;
import com.my_project.LightNovel_web_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final ChapterRepository chapterRepository;

    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentResponse addComment(String username, CommentRequest request, long chapterId) {
        Comment comment = commentMapper.requestToEntity(request);

        User user = userRepository.findByUserName(username).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, username)
        );

        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(
                ()->new AppException(ErrorCode.INVALID_REQUEST, chapterId)
        );

        comment.setUser(user);
        comment.setChapter(chapter);

        return commentMapper.entityToResponse(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponse> findAllByChapterId(long chapterId) {
        List<Comment> comments = commentRepository.findByChapterId(chapterId);
        if (comments.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND, null);
        }

        return comments.stream().map(commentMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteComment(long commentId) {
        if (commentRepository.existsById(commentId)) {
            throw new AppException(ErrorCode.INVALID_REQUEST, commentId);
        }
        chapterRepository.deleteById(commentId);
    }
}
