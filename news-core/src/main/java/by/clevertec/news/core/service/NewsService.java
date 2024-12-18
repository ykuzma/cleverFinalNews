package by.clevertec.news.core.service;

import by.clevertec.news.core.entity.dto.NewsDto;
import by.clevertec.news.core.entity.dto.NewsWithComments;

import java.util.List;
import java.util.UUID;

public interface NewsService {

    NewsDto addNews(NewsDto newsDto);
    void delete(UUID id);
    NewsDto update(NewsDto newsDto, UUID id);
    List<NewsDto> findAll();
    NewsWithComments findNewsWithComments(UUID id);

}
