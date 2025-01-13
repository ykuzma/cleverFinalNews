package by.clevertec.news.api.mapper;

import by.clevertec.news.api.entity.dto.CommentCreate;
import by.clevertec.news.api.entity.dto.CommentResponse;
import by.clevertec.news.api.entity.dto.CommentUpdate;
import by.clevertec.news.core.domain.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    CommentResponse toResponse(Comment comment);
    Comment toDomain(CommentCreate commentCreate);
    Comment toDomain(CommentUpdate commentUpdate);
}
