package by.clevertec.news.api.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsWithComments {
    @NotNull
    private UUID id;
    @NotNull
    private LocalDateTime time;

    @NotBlank(message = "Title can not be empty")
    @Size(min = 3, max = 255)
    private String title;

    @NotBlank(message = "Text can not be empty")
    @Size(min = 3)
    private String text;

    private List<CommentResponse> comments = new ArrayList<>();

}
