package by.clevertec.news.core.port.out;

import by.clevertec.news.core.domain.News;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface NewsOutputPort {
    News save(News news);
    News update(News news);
    void delete(News news);

    News findById(UUID id);

    List<News> findAll(PageRequest pageRequest);

}
