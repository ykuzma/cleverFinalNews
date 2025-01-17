package by.clevertec.news.api.controllers;

import by.clevertec.news.api.AbstractTest;
import by.clevertec.news.api.entity.dto.NewsCreate;
import by.clevertec.news.api.entity.dto.NewsResponse;
import by.clevertec.news.api.mapper.CommentMapper;
import by.clevertec.news.api.mapper.NewsMapper;
import by.clevertec.news.core.domain.News;
import by.clevertec.news.core.service.CommentUseCase;
import by.clevertec.news.core.service.NewsUseCase;
import by.clevertec.news.core.util.Pagination;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(NewsController.class)
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class NewsControllerTest extends AbstractTest {

    @MockitoBean
    NewsUseCase newsUseCase;

    @MockitoBean
    CommentUseCase commentUseCase;
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    NewsMapper mapper;

    @MockitoBean
    CommentMapper commentMapper;


    @Test
    void whenGetNewsById_thenReturnHttpOk() throws Exception {
        //given
        News news = testHelper.getObject(News.class);
        UUID id = news.getId();
        NewsResponse newsResponse = testHelper.getObject(NewsResponse.class);
        newsResponse.setId(id);

        when(newsUseCase.findById(id)).thenReturn(news);
        when(mapper.toResponse(news)).thenReturn(newsResponse);

        //when//then
        mockMvc.perform(get("/news/{newsId}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(newsResponse)))
                .andDo(docHelper.getDocumentForGetNewsById());
    }



    @Test
    void whenGetAllNews_thenReturnHttpOk() throws Exception {
        //given
        List<NewsResponse> listResponses = testHelper.getObjectList(NewsResponse.class, 5);
        List<News> listNews = testHelper.getObjectList(News.class, 5);
        when(newsUseCase.findAll(ArgumentMatchers.any(Pagination.class))).thenReturn(listNews);
        when(mapper.toResponseList(listNews)).thenReturn(listResponses);
        //when//then
        mockMvc.perform(get("/news")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(listResponses)))
                .andDo(docHelper.getDocumentForGetAllNews());
    }

    @Test
    void whenPostNews_thenReturnHttpCreated() throws Exception {
        //given
        NewsCreate newsCreate = testHelper.getObject(NewsCreate.class);
        NewsResponse newsResponse = testHelper.getObject(NewsResponse.class);
        News news = testHelper.getObject(News.class);
        when(mapper.toDomain(newsCreate)).thenReturn(news);
        when(newsUseCase.addNews(news)).thenReturn(news);
        when(mapper.toResponse(news)).thenReturn(newsResponse);
        //when//then
        mockMvc.perform(post("/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newsCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(newsResponse)))
                .andDo(docHelper.getDocumentForPostNews());
    }
}