package by.clevertec.news.api.integration.client;

import by.clevertec.news.api.client.CommentsClient;
import by.clevertec.news.api.entity.dto.CommentResponse;
import by.clevertec.news.api.entity.dto.CommentSaveForClient;
import by.clevertec.news.api.util.TestHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@EnableFeignClients
@AutoConfigureWireMock(port = 0)
class CommentsClientTest {

    @Autowired
    CommentsClient client;

    TestHelper testHelper;

    public CommentsClientTest() {
        testHelper = new TestHelper();
    }

    @Test
    void whenGet_shouldReturnCommentsByNewsSuccessfully() throws JsonProcessingException {
        //given
        UUID uuid = UUID.randomUUID();
        List<CommentResponse> comments = testHelper.getObjectList(CommentResponse.class, 10);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/comments/news/" + uuid))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(testHelper.getResponseBody(comments))));
        //when
        List<CommentResponse> actualResponse = client.getCommentsByNews(uuid);
        //then

        assertThat(actualResponse).isEqualTo(comments);
    }

    @Test
    void whenPost_shouldReturnCommentSuccessfully() throws IOException {
        //given
        CommentSaveForClient commentSaveForClient = testHelper.getObject(CommentSaveForClient.class);
        CommentResponse commentResponse = testHelper.getCommentResponse();
        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/comments"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(testHelper.getResponseBody(commentResponse))));
        //when
        CommentResponse actualResponse = client.saveComment(commentSaveForClient);
        //then

        assertThat(actualResponse).isEqualTo(commentResponse);
    }

}
