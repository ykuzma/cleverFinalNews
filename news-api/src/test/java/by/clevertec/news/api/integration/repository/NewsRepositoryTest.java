package by.clevertec.news.api.integration.repository;

import by.clevertec.news.api.entity.NewsEntity;
import by.clevertec.news.api.repository.NewsRepository;
import by.clevertec.news.api.util.TestHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = NewsRepository.class)
@EntityScan(basePackageClasses = NewsEntity.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/insertData.sql")
class NewsRepositoryTest {
    @Autowired
    NewsRepository repository;
    @Autowired
    TestEntityManager entityManager;

    TestHelper testHelper;

    public NewsRepositoryTest() {
        this.testHelper = new TestHelper();
    }

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldReturnRightSize() {
        assertThat(repository.findAll()).hasSize(10);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 5",
            "1, 2",
            "0, 10",
            "2, 4"
    })
    void whenPagination_shouldSuccessful(int pageNumber, int pageSize) {
        //given

        List<NewsEntity> expectedResponse = testHelper.getPageableList(
                pageNumber,
                pageSize);
        //when
        List<NewsEntity> actualResponse = repository
                .findAll(PageRequest.of(pageNumber, pageSize))
                .getContent();
        //then
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void shouldSaveSuccessful() {
        //given
        NewsEntity newsEntity = testHelper.getNewsEntity();
        newsEntity.setId(null);
        //when
        NewsEntity actualResponse = repository.save(newsEntity);
        //then

        assertThat(actualResponse).isEqualTo(entityManager.find(NewsEntity.class, actualResponse.getId()));
    }

    @Test
    void shouldFindByIdSuccessful() {
        //given
        NewsEntity newsEntity = testHelper.getNewsEntity();
        newsEntity.setId(null);
        NewsEntity newsEntityAfterPersist = entityManager.persist(newsEntity);
        //when
        Optional<NewsEntity> actualResponse = repository.findById(newsEntityAfterPersist.getId());
        //then
        assertThat(actualResponse).contains(newsEntityAfterPersist);
    }

    @Test
    void shouldUpdatedSuccessful() {
        //given
        NewsEntity newsEntity = testHelper.getNewsEntity();
        UUID id = testHelper.getNewsEntities().get(0).getId();
        newsEntity.setId(id);
        //when
        NewsEntity actualResponse = repository.save(newsEntity);
        //then
        assertThat(actualResponse).isEqualTo(entityManager.find(NewsEntity.class, id));
    }

    @Test
    void shouldDeletedSuccessful() {
        //given
        NewsEntity newsEntity = testHelper.getNewsEntities().get(0);
        UUID id = newsEntity.getId();
        //when
        repository.delete(newsEntity);
        //then
        assertThat(entityManager.find(NewsEntity.class, id)).isNull();
    }


}
