package by.clevertec.news.core.service.impl;

import by.clevertec.news.core.domain.News;
import by.clevertec.news.core.port.out.CommentOutputPort;
import by.clevertec.news.core.port.out.NewsOutputPort;
import by.clevertec.news.core.util.TestHelper;
import by.clevertec.news.core.util.UtilService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsUseCaseImplTest {

    @Mock
    NewsOutputPort adapter;
    @Mock
    CommentOutputPort commentAdapter;
    @Mock
    UtilService utilService;

    @InjectMocks
    NewsUseCaseImpl service;

    TestHelper helper;

    public NewsUseCaseImplTest() {
        helper = new TestHelper();
    }

    @Test
    void shouldAddNews() {
        //given
        UUID uuid = UUID.randomUUID();
        News newsCreate = News.builder().text("111").title("222").build();
        News expectedResponse = News.builder().id(uuid).text("111").title("222").time(LocalDateTime.MAX).build();

        News newsBeforeRepository = News.builder().time(LocalDateTime.MAX).text("111").title("222").build();;
        News newsAfterRepository = News.builder().id(uuid).text("111").title("222").time(LocalDateTime.MAX).build();

        when(utilService.getCurrentTime()).thenReturn(LocalDateTime.MAX);
        when(adapter.save(newsBeforeRepository)).thenReturn(newsAfterRepository);
        //when
        News actualResponse = service.addNews(newsCreate);
        //then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void shouldDeletePositive() {
        //given
        News news = helper.getNews();
        when(adapter.findById(news.getId())).thenReturn(news);
        //when
        service.delete(news.getId());

        //then
        verify(adapter).delete(news);
        verify(commentAdapter).deleteCommentByNews(news.getId());

    }
/*

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

        when(adapter.findById(news.getId())).thenReturn(Optional.of(news));
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
        when(adapter.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(new PageImpl<>(newsList));
        //when
        List<NewsResponse> actualResponse = service.findAll(new Pagination());
        //then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void findNewsWithComments() {
        //given
        News news = helper.getNews();
        List<CommentDto> comments = helper.getNewsList(CommentDto.class);
        NewsWithComments expectedResponse = NewsWithComments.builder()
                .id(news.getId())
                .time(news.getTime())
                .text(news.getText())
                .title(news.getTitle())
                .comments(comments)
                .build();
        when(adapter.findById(news.getId())).thenReturn(Optional.of(news));
        when(commentsClient.getCommentsByNews(news.getId())).thenReturn(comments);
        //when
        NewsWithComments actualResponse = service.findNewsWithComments(news.getId());
        //then

        assertThat(actualResponse).isEqualTo(expectedResponse);

    }*/
}