package by.clevertec.news.core.service.impl;

import by.clevertec.news.core.entity.News;
import by.clevertec.news.core.entity.dto.NewsCreate;
import by.clevertec.news.core.entity.dto.NewsResponse;
import by.clevertec.news.core.entity.dto.NewsUpdate;
import by.clevertec.news.core.entity.dto.NewsWithComments;
import by.clevertec.news.core.mapper.NewsMapper;
import by.clevertec.news.core.repository.NewsRepository;
import by.clevertec.news.core.service.NewsService;
import by.clevertec.news.core.util.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsServiceImpl implements NewsService {

    private final NewsRepository repository;
    private final NewsMapper mapper;
    private final UtilService utilService;


    @Override
    public NewsResponse addNews(NewsCreate newsCreate) {
        News news = mapper.toEntity(newsCreate);
        news.setTime(utilService.getCurrentTime());
        return mapper.toResponse(repository.save(news));
    }

    @Override
    public void delete(UUID id) {

        repository.delete(repository.findById(id).orElseThrow());

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
