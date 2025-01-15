package by.clevertec.news.api.adapter.secondary;

import by.clevertec.news.api.entity.NewsEntity;
import by.clevertec.news.api.entity.exception.NotFoundDataException;
import by.clevertec.news.api.mapper.NewsMapper;
import by.clevertec.news.api.repository.NewsRepository;
import by.clevertec.news.core.domain.News;
import by.clevertec.news.core.port.out.NewsOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class NewsSecondaryAdapter implements NewsOutputPort {

    private final NewsRepository repository;
    private final NewsMapper mapper;

    @Override
    @CachePut(value = "news", key = "#result.id")
    public News save(News news) {
        NewsEntity newsEntity = repository.save(mapper.toEntity(news));
        return mapper.toDomain(newsEntity);
    }

    @Override
    public News update(News news) {
        NewsEntity newsEntity = repository.save(mapper.toEntity(news));
        return mapper.toDomain(newsEntity);
    }

    @Override
    @CacheEvict(value = "news", key = "#news.id")
    public void delete(News news) {
        repository.delete(mapper.toEntity(news));
    }

    @Cacheable("news")
    @Override
    public News findById(UUID id) {
        return mapper.toDomain(
                repository.findById(id).orElseThrow(
                        () -> new NotFoundDataException(id, NewsEntity.class)));
    }

    @Override
    public List<News> findAll(PageRequest pageRequest) {
        return mapper.toDomainList(
                repository.findAll(pageRequest)
                        .getContent());
    }
}
