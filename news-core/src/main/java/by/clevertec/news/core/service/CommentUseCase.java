package by.clevertec.news.core.service;

import by.clevertec.news.core.domain.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentUseCase {

    Comment createComment(Comment comment, UUID newsId);
    Comment updateComment(Comment comment, UUID commentId, UUID newsId);
    void deleteComment(UUID commentId, UUID newsId);

}
