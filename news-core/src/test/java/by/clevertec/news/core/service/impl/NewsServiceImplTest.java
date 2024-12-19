package by.clevertec.news.core.service.impl;

import by.clevertec.news.core.entity.News;
import by.clevertec.news.core.entity.dto.NewsCreate;
import by.clevertec.news.core.entity.dto.NewsResponse;
import by.clevertec.news.core.mapper.NewsMapperImpl;
import by.clevertec.news.core.repository.NewsRepository;
import by.clevertec.news.core.util.TestHelper;
import by.clevertec.news.core.util.UtilService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
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
}