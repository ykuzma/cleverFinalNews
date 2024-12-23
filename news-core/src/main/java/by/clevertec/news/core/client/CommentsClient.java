package by.clevertec.news.core.client;

import by.clevertec.news.core.entity.dto.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(value = "comments-service", url = "localhost")
public interface CommentsClient {

    @GetMapping("/comments/{id}")
    List<CommentDto> getCommentsByNews(@PathVariable UUID id);
}
