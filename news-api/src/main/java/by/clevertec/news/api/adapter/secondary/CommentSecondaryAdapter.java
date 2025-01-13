package by.clevertec.news.api.adapter.secondary;

import by.clevertec.news.api.client.CommentsClient;
import by.clevertec.news.api.mapper.CommentMapper;
import by.clevertec.news.core.domain.Comment;
import by.clevertec.news.core.port.out.CommentOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommentSecondaryAdapter implements CommentOutputPort {

    private final CommentsClient client;
    private final CommentMapper mapper;

    @Override
    public Comment findById(UUID commentId) {
        return mapper.toDomain(client.getComment(commentId));
    }

    @Override
    public List<Comment> findByNews(UUID newsId) {
        return mapper.toDomainList(client.getCommentsByNews(newsId));
    }

    @Override
    public Comment saveComment(Comment comment) {
        return mapper.toDomain(client.saveComment(mapper.toCommentSave(comment)));
    }

    @Override
    public Comment updateComment(Comment comment, UUID commentId) {
        return mapper.toDomain(client.updateComment(mapper.toCommentUpdate(comment), commentId));
    }

    @Override
    public void deleteComment(UUID commentId) {
        client.deleteComment(commentId);
    }

    @Override
    public void deleteCommentByNews(UUID newsId) {
        client.deleteCommentsByNews(newsId);
    }
}
