package by.clevertec.news.api.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    @NotNull
    private UUID id;

    @NotNull
    private LocalDateTime time;

    @NotBlank(message = "Text can not be empty")
    private String text;

    @NotNull
    private String username;
    private UUID newsId;
}

