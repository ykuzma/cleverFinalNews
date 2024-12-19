package by.clevertec.news.core.service.impl;

import by.clevertec.news.core.entity.News;
import by.clevertec.news.core.entity.dto.NewsCreate;
import by.clevertec.news.core.entity.dto.NewsResponse;
import by.clevertec.news.core.entity.dto.NewsUpdate;
import by.clevertec.news.core.mapper.NewsMapperImpl;
import by.clevertec.news.core.repository.NewsRepository;
import by.clevertec.news.core.util.Pagination;
import by.clevertec.news.core.util.TestHelper;
import by.clevertec.news.core.util.UtilService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    @Mock
    NewsRepository repository;
    @Mock
    UtilService utilService;
    @Spy
    NewsMapperImpl mapper;
    @InjectMocks
    NewsServiceImpl service;

    TestHelper helper;

    public NewsServiceImplTest() {
        helper = new TestHelper();
    }

    @Test
    void shouldAddNews() {
        //given
        UUID uuid = UUID.randomUUID();
        NewsCreate newsCreate =  helper.getNewsCreate();
        NewsResponse expectedResponse = new NewsResponse(uuid, LocalDateTime.MAX, newsCreate.getTitle(), newsCreate.getText());

        News newsBeforeRepository = new News(null, LocalDateTime.MAX, newsCreate.getTitle(), newsCreate.getText());
        News newsAfterRepository = new News(uuid, LocalDateTime.MAX, newsCreate.getTitle(), newsCreate.getText());

        when(utilService.getCurrentTime()).thenReturn(LocalDateTime.MAX);
        when(repository.save(newsBeforeRepository)).thenReturn(newsAfterRepository);
        //when
        NewsResponse actualResponse = service.addNews(newsCreate);
        //then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void shouldDeletePositive() {
        //given
        News news = helper.getNews();
        when(repository.findById(news.getId())).thenReturn(Optional.of(news));
        //when
        service.delete(news.getId());

        //then
        verify(repository).delete(news);
    }

    @Test
    void shouldDeleteNegative() {
        //given
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        //when, then
        assertThrows(NoSuchElementException.class, () -> service.delete(id));
    }

    @Test
    void shouldSuccessfulUpdateNews() {
        //given
        NewsUpdate newsUpdate = helper.getNewsUpdate();
        News news = helper.getNews();
        NewsResponse expectedResponse = NewsResponse.builder()
                .id(news.getId())
                .time(news.getTime())
                .text(newsUpdate.getText())
                .title(newsUpdate.getTitle())
                .build();

        when(repository.findById(news.getId())).thenReturn(Optional.of(news));
        //when
        NewsResponse actualResponse = service.update(newsUpdate, news.getId());
        //then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void shouldFindAllWithPagination() {
        //given
        List<News> newsList = helper.getNewsList(News.class);
        List<NewsResponse> expectedResponse = mapper.toResponseList(newsList);
        when(repository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(new PageImpl<>(newsList));
        //when
        List<NewsResponse> actualResponse = service.findAll(new Pagination());
        //then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
}