package by.clevertec.news.api.client;

import by.clevertec.news.api.entity.dto.CommentResponse;
import by.clevertec.news.api.entity.dto.CommentSaveForClient;
import by.clevertec.news.api.entity.dto.CommentUpdateForClient;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "comments-service", url = "${application.service.url}")
public interface CommentsClient {

    @GetMapping("/comments/news/{newsId}")
    List<CommentResponse> getCommentsByNews(@PathVariable @NotNull UUID newsId);

    @GetMapping("/comments/{commentId}")
    CommentResponse getComment(@PathVariable @NotNull UUID commentId);

    @PostMapping("/comments")
    CommentResponse saveComment(@RequestBody @Validated CommentSaveForClient commentSave);

    @PutMapping("/comments/{commentId}")
    CommentResponse updateComment(@RequestBody @Validated CommentUpdateForClient commentUpdate,
                                   @PathVariable @NotNull UUID commentId);

    @DeleteMapping("/comments/{commentId}")
    void deleteComment(@PathVariable @NotNull UUID commentId);

    @DeleteMapping("/comments/news/{newsId}")
    void deleteCommentsByNews(@PathVariable @NotNull UUID newsId);
}
