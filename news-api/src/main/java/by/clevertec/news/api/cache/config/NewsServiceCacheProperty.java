package by.clevertec.news.api.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "application.cache")
public class NewsServiceCacheProperty {

    private String strategy;
    private int limit;

}
