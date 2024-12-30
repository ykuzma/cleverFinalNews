package by.clevertec.news.core.domain;

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
public class News {

    private UUID id;

    private LocalDateTime time;

    private String title;

    private String text;

    public News update(News newsUpdate) {
        return News.builder()
                .id(id)
                .time(time)
                .text(newsUpdate.getText())
                .title(newsUpdate.getTitle())
                .build();
    }
}
