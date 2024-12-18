package by.clevertec.news.core.mapper;

import by.clevertec.news.core.entity.News;
import by.clevertec.news.core.entity.dto.NewsCreate;
import by.clevertec.news.core.entity.dto.NewsResponse;
import by.clevertec.news.core.entity.dto.NewsUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsMapper {
    News toEntity(NewsCreate newsCreate);
    News toEntity(NewsUpdate newsUpdate);
    NewsResponse toResponse(News news);
    List<NewsResponse> toResponseList(List<News> news);


}
