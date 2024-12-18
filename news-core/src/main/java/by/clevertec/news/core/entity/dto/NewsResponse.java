package by.clevertec.news.core.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class NewsResponse {
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
}
