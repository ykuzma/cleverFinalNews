package by.clevertec.news.api.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

public class RestDocHelper {

    private final String pathForSnippetsGetNewsById = "newsController/getNewsById";
    private final String pathForSnippetsGetAllNews = "newsController/getAllNews";
    private final String pathForSnippetsPostNews = "newsController/postNews";
    private final String pathParamNewsIdName = "newsId";
    private final String pathParamNewsIdDescription = "News UUID id";

    private final FieldDescriptor[] newsResponseFields = {
            fieldWithPath("id").description("News UUID id").type(UUID.class),
            fieldWithPath("time").description("time create the news").type(LocalDateTime.class),
            fieldWithPath("title").description("News title"),
            fieldWithPath("text").description("News text")};

    private final FieldDescriptor[] newsCreateFields = {
            fieldWithPath("title").description("News title"),
            fieldWithPath("text").description("News text")};

    @NotNull
    public RestDocumentationResultHandler getDocumentForGetNewsById() {
        return document(pathForSnippetsGetNewsById,
                pathParameters(parameterWithName(pathParamNewsIdName)
                        .description(pathParamNewsIdDescription)),
                responseFields(newsResponseFields));
    }

    @NotNull
    public RestDocumentationResultHandler getDocumentForGetAllNews() {
        return document(pathForSnippetsGetAllNews,
                responseFields(fieldWithPath("[]")
                        .description("A list of news").type(List.class))
                        .andWithPrefix("[].", getNewsResponseFields()));
    }

    @NotNull
    public RestDocumentationResultHandler getDocumentForPostNews() {
        return document(pathForSnippetsPostNews,
                requestBody(),
                responseFields(newsResponseFields));
    }


    public FieldDescriptor[] getNewsResponseFields() {
        return newsResponseFields;
    }

    public String getSnippetPathGetNewsById() {
        return pathForSnippetsGetNewsById;
    }
}
