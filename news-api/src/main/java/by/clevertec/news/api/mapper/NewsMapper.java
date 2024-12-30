package by.clevertec.news.api.mapper;

import by.clevertec.news.api.entity.NewsEntity;
import by.clevertec.news.api.entity.dto.NewsCreate;
import by.clevertec.news.api.entity.dto.NewsResponse;
import by.clevertec.news.api.entity.dto.NewsUpdate;
import by.clevertec.news.api.entity.dto.NewsWithComments;
import by.clevertec.news.core.domain.News;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsMapper {
    News toDomain(NewsCreate newsCreate);
    News toDomain(NewsUpdate newsUpdate);

    News toDomain(NewsEntity newsEntity);
    List<News> toDomainList(List<NewsEntity> entities);

    NewsEntity toEntity(News news);
    NewsResponse toResponse(News news);

    NewsWithComments toResponseWithComments(News news);
    List<NewsResponse> toResponseList(List<News> news);

}
