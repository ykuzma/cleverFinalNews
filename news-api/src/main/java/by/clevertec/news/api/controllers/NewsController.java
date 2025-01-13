package by.clevertec.news.api.controllers;

import by.clevertec.news.api.entity.dto.CommentCreate;
import by.clevertec.news.api.entity.dto.CommentResponse;
import by.clevertec.news.api.entity.dto.CommentUpdate;
import by.clevertec.news.api.entity.dto.NewsCreate;
import by.clevertec.news.api.entity.dto.NewsResponse;
import by.clevertec.news.api.entity.dto.NewsUpdate;
import by.clevertec.news.api.entity.dto.NewsWithComments;
import by.clevertec.news.api.mapper.CommentMapper;
import by.clevertec.news.api.mapper.NewsMapper;
import by.clevertec.news.core.service.CommentUseCase;
import by.clevertec.news.core.service.NewsUseCase;
import by.clevertec.news.core.util.Pagination;
import by.clevertec.starter.logging.annotation.Logging;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsUseCase newsUseCase;
    private final CommentUseCase commentUseCase;
    private final CommentMapper commentMapper;
    private final NewsMapper mapper;

    @GetMapping
    @Logging
    public ResponseEntity<List<NewsResponse>> getAllNews(@Validated Pagination pagination) {

        return new ResponseEntity<>(mapper.toResponseList(newsUseCase.findAll(pagination)), HttpStatus.OK);
    }

    @PostMapping
    @Logging
    public ResponseEntity<NewsResponse> postNews(@Validated @RequestBody NewsCreate newsCreate) {
        return new ResponseEntity<>(mapper.toResponse(newsUseCase.addNews(mapper.toDomain(newsCreate))),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Logging
    public ResponseEntity<NewsResponse> getNews(@PathVariable UUID id) {

        return new ResponseEntity<>(mapper.toResponse(newsUseCase.findById(id)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Logging
    public ResponseEntity<NewsResponse> updateNews(@Validated @RequestBody NewsUpdate newsUpdate,
                                                   @PathVariable UUID id) {
        return new ResponseEntity<>(mapper.toResponse(newsUseCase.update(mapper.toDomain(newsUpdate), id)),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Logging
    public ResponseEntity<Void> deleteNews(@PathVariable UUID id) {
        newsUseCase.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/comments")
    @Logging
    public ResponseEntity<NewsWithComments> getNewsWithComments(@PathVariable UUID id) {

        return new ResponseEntity<>(mapper.toResponseWithComments(newsUseCase.findWithCommentsById(id)),
                HttpStatus.OK);
    }

    @PostMapping("/{newsId}/comments")
    @Logging
    public ResponseEntity<CommentResponse> postComment(@Validated @RequestBody CommentCreate commentCreate,
                                                       @PathVariable UUID newsId) {
        return new ResponseEntity<>(
                commentMapper.toResponse(commentUseCase.createComment(
                        commentMapper.toDomain(commentCreate),
                        newsId)),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{newsId}/comments/{commentId}")
    @Logging
    public ResponseEntity<CommentResponse> updateComment(@Validated @RequestBody CommentUpdate commentUpdate,
                                                         @PathVariable UUID newsId,
                                                         @PathVariable UUID commentId) {
        return new ResponseEntity<>(
                commentMapper.toResponse(commentUseCase.updateComment(
                        commentMapper.toDomain(commentUpdate),
                        commentId,
                        newsId)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{newsId}/comments/{commentId}")
    @Logging
    public ResponseEntity<Void> deleteComment(@PathVariable UUID newsId,
                                              @PathVariable UUID commentId) {
        commentUseCase.deleteComment(commentId, newsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
