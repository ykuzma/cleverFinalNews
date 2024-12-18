package by.clevertec.news.core.service.impl;

import by.clevertec.news.core.entity.dto.NewsCreate;
import by.clevertec.news.core.entity.dto.NewsResponse;
import by.clevertec.news.core.entity.dto.NewsUpdate;
import by.clevertec.news.core.entity.dto.NewsWithComments;
import by.clevertec.news.core.mapper.NewsMapper;
import by.clevertec.news.core.repository.NewsRepository;
import by.clevertec.news.core.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository repository;
    private final NewsMapper mapper;


    @Override
    public NewsResponse addNews(NewsCreate newsDto) {

        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public NewsResponse update(NewsUpdate newsUpdate, UUID id) {
        return null;
    }

    @Override
    public List<NewsResponse> findAll() {
        return null;
    }

    @Override
    public NewsWithComments findNewsWithComments(UUID id) {
        return null;
    }
}
