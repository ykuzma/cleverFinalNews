package by.clevertec.news.core.mapper;

import by.clevertec.news.core.entity.News;
import by.clevertec.news.core.entity.dto.NewsCreate;
import by.clevertec.news.core.entity.dto.NewsResponse;
import by.clevertec.news.core.entity.dto.NewsUpdate;
import by.clevertec.news.core.entity.dto.NewsWithComments;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsMapper {
    News toEntity(NewsCreate newsCreate);
    News toEntity(NewsUpdate newsUpdate);
    NewsResponse toResponse(News news);
    List<NewsResponse> toResponseList(List<News> news);
    NewsWithComments toNewsWithComments(News news);

    void updateEntity(@MappingTarget News news, NewsUpdate newsUpdate);


}
