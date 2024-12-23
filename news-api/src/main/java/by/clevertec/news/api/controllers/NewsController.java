package by.clevertec.news.api.controllers;

import by.clevertec.news.core.entity.dto.NewsCreate;
import by.clevertec.news.core.entity.dto.NewsResponse;
import by.clevertec.news.core.service.NewsService;
import by.clevertec.news.core.util.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService service;


    @GetMapping("/find")
    public ResponseEntity<List<NewsResponse>> getAllNews(@Validated Pagination pagination) {
        service.addNews(new NewsCreate("555", "yyyyyyyyyy"));

        return new ResponseEntity<>(service.findAll(pagination), HttpStatus.OK);
    }

}
