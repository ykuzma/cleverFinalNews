package by.clevertec.news.core.mapper;

import by.clevertec.news.core.entity.News;
import by.clevertec.news.core.entity.dto.NewsCreate;
import by.clevertec.news.core.util.TestHelper;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class NewsMapperTest {

    NewsMapper mapper;
    TestHelper helper;

    public NewsMapperTest() {
        mapper = new NewsMapperImpl();
        helper = new TestHelper();
    }

    @RepeatedTest(10)
    void shouldMappingNewsCreateDtoToEntity() {
        //given
        NewsCreate newsCreate = helper.getNewsCreate();
        //when
        News news = mapper.toEntity(newsCreate);
        //then
        assertThat(news)
                .isNotNull()
                .hasFieldOrPropertyWithValue("title", newsCreate.getTitle())
                .hasFieldOrPropertyWithValue("text", newsCreate.getText())
                .hasFieldOrPropertyWithValue("id", null)
                .hasFieldOrPropertyWithValue("time", null);
    }
}