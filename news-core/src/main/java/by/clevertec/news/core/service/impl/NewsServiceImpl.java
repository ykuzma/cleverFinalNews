package by.clevertec.news.core.service.impl;

import by.clevertec.news.core.entity.dto.NewsDto;
import by.clevertec.news.core.entity.dto.NewsWithComments;
import by.clevertec.news.core.service.NewsService;

import java.util.List;
import java.util.UUID;

public class NewsServiceImpl implements NewsService {
    @Override
    public NewsDto addNews(NewsDto newsDto) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public NewsDto update(NewsDto newsDto, UUID id) {
        return null;
    }

    @Override
    public List<NewsDto> findAll() {
        return null;
    }

    @Override
    public NewsWithComments findNewsWithComments(UUID id) {
        return null;
    }
}
