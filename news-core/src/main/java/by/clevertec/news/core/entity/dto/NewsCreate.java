package by.clevertec.news.core.entity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewsCreate {
    @NotBlank(message = "Title can not be empty")
    private String title;
    @NotBlank(message = "Text can not be empty")
    private String text;

}
