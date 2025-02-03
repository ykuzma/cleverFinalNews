package by.clevertec.news.api.cache;

import by.clevertec.news.api.cache.config.NewsServiceCacheProperty;
import lombok.Data;
import org.springframework.cache.Cache;

@Data
public class NewsCacheFactory implements CustomCacheFactory {

    private final NewsServiceCacheProperty property;

    @Override
    public Cache getInstance(String name) {
        int limit = property.getLimit();
        String strategy = property.getStrategy();

        return switch (strategy) {
            case "lfu" -> new NewsServiceCacheLFU(name, limit);
            case "lru" -> new NewsServiceCacheLRU(name, limit);
            default -> throw new IllegalArgumentException("Illegal strategy: " + strategy);
        };
    }
}
