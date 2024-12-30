package by.clevertec.news.api.client;

import by.clevertec.news.api.entity.dto.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "comments-service", url = "${application.service.url}")
public interface CommentsClient {

    @GetMapping("/comments/news/{newsId}")
    List<CommentDto> getCommentsByNews(@PathVariable UUID newsId);
}
