package by.clevertec.news.api.util;

import by.clevertec.news.api.entity.NewsEntity;
import by.clevertec.news.api.entity.dto.CommentResponse;
import by.clevertec.news.api.entity.dto.CommentSaveForClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jeasy.random.EasyRandom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public class TestHelper {
    private final EasyRandom easyRandom = new EasyRandom();
    private final List<NewsEntity> newsEntities;
    private final ObjectMapper objectMapper = new ObjectMapper();
    {

    }

    public TestHelper() {
        newsEntities = List.of(
                new NewsEntity(UUID.fromString("760045a3-32aa-4393-8dfd-d57dc95c4934"), LocalDateTime.parse("2004-05-05T00:00:00.000000"), "111", "1111111111"),
                new NewsEntity(UUID.fromString("760045a3-32aa-4393-8dfd-d57dc95c4935"), LocalDateTime.parse("2004-05-05T00:00:00.000000"), "222", "2222222222"),
                new NewsEntity(UUID.fromString("760045a3-32aa-4393-8dfd-d57dc95c4936"), LocalDateTime.parse("2004-05-05T00:00:00.000000"), "333", "3333333333"),
                new NewsEntity(UUID.fromString("760045a3-32aa-4393-8dfd-d57dc95c4937"), LocalDateTime.parse("2004-05-05T00:00:00.000000"), "444", "4444444444"),
                new NewsEntity(UUID.fromString("760045a3-32aa-4393-8dfd-d57dc95c4938"), LocalDateTime.parse("2004-05-05T00:00:00.000000"), "555", "5555555555"),
                new NewsEntity(UUID.fromString("760045a3-32aa-4393-8dfd-d57dc95c4939"), LocalDateTime.parse("2004-05-05T00:00:00.000000"), "666", "6666666666"),
                new NewsEntity(UUID.fromString("760045a3-32aa-4393-8dfd-d57dc95c4930"), LocalDateTime.parse("2004-05-05T00:00:00.000000"), "777", "7777777777"),
                new NewsEntity(UUID.fromString("760045a3-32aa-4393-8dfd-d57dc95c4931"), LocalDateTime.parse("2004-05-05T00:00:00.000000"), "888", "8888888888"),
                new NewsEntity(UUID.fromString("760045a3-32aa-4393-8dfd-d57dc95c4932"), LocalDateTime.parse("2004-05-05T00:00:00.000000"), "999", "9999999999"),
                new NewsEntity(UUID.fromString("760045a3-32aa-4393-8dfd-d57dc95c4933"), LocalDateTime.parse("2004-05-05T00:00:00.000000"), "000", "0000000000")
        );


        objectMapper.registerModule(new JavaTimeModule());
    }

    public <T> List<T> getObjectList(Class<T> clazz, int size) {
        return easyRandom.objects(clazz, size).toList();}

    public <T>T getObject(Class<T> clazz) {
        return easyRandom.nextObject(clazz);
    }

    public NewsEntity getNewsEntity() {
        return easyRandom.nextObject(NewsEntity.class);}

    public CommentResponse getCommentResponse() {
        return easyRandom.nextObject(CommentResponse.class);
    }


    public List<NewsEntity> getNewsEntities() {
        return newsEntities;
    }

    public List<NewsEntity> getPageableList(int pageNumber, int pageSize){
        return newsEntities.stream().skip((long) pageNumber * pageSize).limit(pageSize).toList();
    }

    public String getResponseBody(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
