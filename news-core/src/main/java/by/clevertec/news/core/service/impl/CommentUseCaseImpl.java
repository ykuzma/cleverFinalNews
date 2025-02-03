package by.clevertec.news.core.service.impl;

import by.clevertec.news.core.domain.Comment;
import by.clevertec.news.core.port.out.CommentOutputPort;
import by.clevertec.news.core.port.out.NewsOutputPort;
import by.clevertec.news.core.service.CommentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommentUseCaseImpl implements CommentUseCase {

    private final CommentOutputPort commentAdapter;
    private final NewsOutputPort newsAdapter;

    @Override
    public Comment createComment(Comment comment, UUID newsId) {
        newsAdapter.findById(newsId);
        comment.setNewsId(newsId);
        return commentAdapter.saveComment(comment);
    }

    @Override
    public Comment updateComment(Comment comment, UUID commentId, UUID newsId) {
        newsAdapter.findById(newsId);
        comment.setNewsId(newsId);
        return commentAdapter.updateComment(comment, commentId);
    }

    @Override
    public void deleteComment(UUID commentId, UUID newsId) {
        newsAdapter.findById(newsId);
        commentAdapter.deleteComment(commentId);
    }
}
