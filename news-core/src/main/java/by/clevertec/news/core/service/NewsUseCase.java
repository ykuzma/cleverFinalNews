package by.clevertec.news.core.service;

import by.clevertec.news.core.domain.News;
import by.clevertec.news.core.util.Pagination;

import java.util.List;
import java.util.UUID;

public interface NewsUseCase {

    News findById(UUID id);
    News findWithCommentsById(UUID id);

    News addNews(News news);
    void delete(UUID id);
    News update(News news, UUID id);
    List<News> findAll(Pagination pagination);


}
