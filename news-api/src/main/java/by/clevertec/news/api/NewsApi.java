package by.clevertec.news.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"by.clevertec.news.core", "by.clevertec.news.api"})
@EnableFeignClients
public class NewsApi {
    public static void main(String[] args) {
        SpringApplication.run(NewsApi.class, args);
    }
}
