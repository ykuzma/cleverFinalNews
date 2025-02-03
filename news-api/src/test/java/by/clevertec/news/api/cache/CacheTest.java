package by.clevertec.news.api.cache;

import by.clevertec.news.api.AbstractTest;
import by.clevertec.news.api.adapter.secondary.NewsSecondaryAdapter;
import by.clevertec.news.api.cache.config.CacheConfig;
import by.clevertec.news.api.entity.NewsEntity;
import by.clevertec.news.api.mapper.NewsMapperImpl;
import by.clevertec.news.api.repository.NewsRepository;
import by.clevertec.news.api.util.TestHelper;
import by.clevertec.news.core.domain.News;
import by.clevertec.news.core.port.out.NewsOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@DataJpaTest
@Sql("/insertData.sql")
@ContextConfiguration(classes = {NewsSecondaryAdapter.class, NewsMapperImpl.class, CacheConfig.class})
@EnableJpaRepositories(basePackages = {"by.clevertec.news.api.*"})
@EntityScan(basePackageClasses = NewsEntity.class)
class CacheTest extends AbstractTest {


    @Autowired
    NewsOutputPort adapter;
    @MockitoSpyBean
    NewsMapperImpl mapper;
    @MockitoBean
    NewsRepository repository;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    void clearCache() {
        cacheManager.getCache(TestHelper.NEWS_CACHE).clear();
    }

    @Test
    void whenFindMoreThenOneTimes_shouldUsedCacheInsteadRepository() {

        //given
        NewsEntity newsEntity = testHelper.getNewsEntity();
        UUID uuid = newsEntity.getId();
        when(repository.findById(uuid)).thenReturn(Optional.of(newsEntity));
        //when
        for (int i = 0; i < 3; i++) {
            adapter.findById(uuid);
        }
        //then
        verify(repository, times(1)).findById(uuid);
    }

    @Test
    void whenDeleteFromRepository_shouldDeleteFromCache() {

        //given
        News news = testHelper.getNews();
        NewsEntity newsEntity = mapper.toEntity(news);
        UUID uuid = news.getId();
        when(repository.save(newsEntity)).thenReturn(newsEntity);

        //when
        adapter.save(news);
        adapter.delete(news);
        News actualResponse = cacheManager.getCache(TestHelper.NEWS_CACHE).get(uuid, News.class);
        //then

        assertThat(actualResponse).isNull();
    }

    @Test
    void whenPutToRepository_shouldPutToCache() {

        //given
        NewsEntity newsEntity = testHelper.getNewsEntity();
        News news = mapper.toDomain(newsEntity);
        UUID uuid = news.getId();
        when(repository.save(newsEntity)).thenReturn(newsEntity);

        //when
        adapter.save(news);
        News actualResponse = cacheManager.getCache(TestHelper.NEWS_CACHE).get(uuid, News.class);
        //then

        assertThat(actualResponse).isEqualTo(news);
    }

    @Test
    void whenUpdateRepository_shouldUpdateCache() {

        //given
        News news = testHelper.getNews();
        NewsEntity newsEntity = mapper.toEntity(news);
        UUID uuid = news.getId();
        NewsEntity updatedNewsEntity = testHelper.getNewsEntity();
        updatedNewsEntity.setId(uuid);
        News updatedNews = mapper.toDomain(updatedNewsEntity);

        when(repository.save(newsEntity)).thenReturn(newsEntity);
        when(repository.save(updatedNewsEntity)).thenReturn(updatedNewsEntity);

        //when
        adapter.save(news);
        adapter.update(updatedNews);
        News actualResponse = cacheManager.getCache(TestHelper.NEWS_CACHE).get(uuid, News.class);
        //then

        assertThat(actualResponse).isEqualTo(updatedNews);
    }

    @Test
    void whenGetFromRepositoryAndNotPresentInCache_shouldPutToCache() {

        //given
        NewsEntity newsEntity = testHelper.getNewsEntity();
        News news = mapper.toDomain(newsEntity);
        UUID uuid = news.getId();
        when(repository.findById(uuid)).thenReturn(Optional.of(newsEntity));

        //when
        adapter.findById(uuid);
        News actualResponse = cacheManager.getCache(TestHelper.NEWS_CACHE).get(uuid, News.class);
        //then

        assertThat(actualResponse).isEqualTo(news);
    }

    @Test
    void whenCacheFull_shouldEvict() {

        //given
        List<NewsEntity> newsEntities = testHelper.getNewsEntities();
        UUID id = newsEntities.get(0).getId();
        for (int i = 0; i < 3; i++) {
            when(repository.save(newsEntities.get(i))).thenReturn(newsEntities.get(i));
            adapter.save(mapper.toDomain(newsEntities.get(i)));
        }
        //when
        News actualResponse = cacheManager.getCache(TestHelper.NEWS_CACHE).get(id, News.class);

        //then

        assertThat(actualResponse).isNull();
    }

}
