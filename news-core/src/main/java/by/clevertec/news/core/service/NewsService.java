package by.clevertec.news.core.service;

import by.clevertec.news.core.entity.dto.NewsCreate;
import by.clevertec.news.core.entity.dto.NewsResponse;
import by.clevertec.news.core.entity.dto.NewsUpdate;
import by.clevertec.news.core.entity.dto.NewsWithComments;
import by.clevertec.news.core.util.Pagination;

import java.util.List;
import java.util.UUID;

public interface NewsService {

    NewsResponse addNews(NewsCreate newsCreate);
    void delete(UUID id);
    NewsResponse update(NewsUpdate newsUpdate, UUID id);
    List<NewsResponse> findAll(Pagination pagination);
    NewsWithComments findNewsWithComments(UUID id);

}
