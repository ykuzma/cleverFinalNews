package by.clevertec.news.api.mapper;

import by.clevertec.news.api.entity.dto.CommentCreate;
import by.clevertec.news.api.entity.dto.CommentResponse;
import by.clevertec.news.api.entity.dto.CommentSaveForClient;
import by.clevertec.news.api.entity.dto.CommentUpdate;
import by.clevertec.news.api.entity.dto.CommentUpdateForClient;
import by.clevertec.news.core.domain.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    CommentResponse toResponse(Comment comment);
    Comment toDomain(CommentCreate commentCreate);
    Comment toDomain(CommentUpdate commentUpdate);
    Comment toDomain(CommentResponse commentResponse);

    CommentSaveForClient toCommentSave(Comment comment);
    CommentUpdateForClient toCommentUpdate(Comment comment);
    List<Comment> toDomainList(List<CommentResponse> commentResponseList);


}
