package by.clevertec.news.core.repository;

import by.clevertec.news.core.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID>, JpaSpecificationExecutor<News> {
}
