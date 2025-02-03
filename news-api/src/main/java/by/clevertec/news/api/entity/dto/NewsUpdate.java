package by.clevertec.news.api.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsUpdate {
    @NotBlank(message = "Title can not be empty")
    @Size(min = 3, max = 255)
    private String title;

    @NotBlank(message = "Text can not be empty")
    @Size(min = 3)
    private String text;
}
