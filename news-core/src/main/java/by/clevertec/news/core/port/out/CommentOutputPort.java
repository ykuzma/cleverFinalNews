package by.clevertec.news.core.port.out;

import by.clevertec.news.core.domain.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentOutputPort {

    Comment findById(UUID commentId);
    List<Comment> findByNews(UUID newsId);
    Comment saveComment(Comment comment);
    Comment updateComment(Comment comment, UUID commentID);
    void deleteComment(UUID commentId);

    void deleteCommentByNews(UUID newsId);

}
