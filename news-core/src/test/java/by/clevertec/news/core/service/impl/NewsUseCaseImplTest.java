package by.clevertec.news.core.service.impl;

import by.clevertec.news.core.domain.Comment;
import by.clevertec.news.core.domain.News;
import by.clevertec.news.core.port.out.CommentOutputPort;
import by.clevertec.news.core.port.out.NewsOutputPort;
import by.clevertec.news.core.util.Pagination;
import by.clevertec.news.core.util.TestHelper;
import by.clevertec.news.core.util.UtilService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
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

        News newsBeforeRepository = News.builder().time(LocalDateTime.MAX).text("111").title("222").build();
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


    @Test
    void shouldSuccessfulUpdateNews() {
        //given
        News newsUpdate = News.builder().text("update").title("update").build();
        News news = helper.getNews();
        News expectedResponse = News.builder()
                .id(news.getId())
                .time(news.getTime())
                .text(newsUpdate.getText())
                .title(newsUpdate.getTitle())
                .build();

        when(adapter.findById(news.getId())).thenReturn(news);
        //when
        News actualResponse = service.update(newsUpdate, news.getId());
        //then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void shouldFindAllWithPagination() {
        //given
        List<News> newsList = helper.getObjectList(News.class);
        Pagination pagination = new Pagination();
        List<News> expectedResponse = newsList.stream()
                .skip((long) pagination.getPageNumber() * pagination.getPageSize())
                .limit(pagination.getPageSize())
                .toList();
        when(adapter.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(expectedResponse);
        //when
        List<News> actualResponse = service.findAll(pagination);
        //then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }
    @Test
    void findNewsWithComments() {
        //given
        News news = helper.getNews();
        List<Comment> comments = helper.getObjectList(Comment.class);
        News expectedResponse = News.builder()
                .id(news.getId())
                .time(news.getTime())
                .text(news.getText())
                .title(news.getTitle())
                .comments(comments)
                .build();
        when(adapter.findById(news.getId())).thenReturn(news);
        when(commentAdapter.findByNews(news.getId())).thenReturn(comments);
        //when
        News actualResponse = service.findWithCommentsById(news.getId());
        //then

        assertThat(actualResponse).isEqualTo(expectedResponse);

    }
}