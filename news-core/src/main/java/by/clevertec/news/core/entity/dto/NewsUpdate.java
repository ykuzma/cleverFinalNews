package by.clevertec.news.core.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewsUpdate {
    @NotBlank(message = "Title can not be empty")
    @Size(min = 3, max = 255)
    private String title;

    @NotBlank(message = "Text can not be empty")
    @Size(min = 3)
    private String text;
}
