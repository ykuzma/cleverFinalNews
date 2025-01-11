package by.clevertec.news.api.cache;

import org.springframework.cache.Cache;

public interface CustomCacheFactory {
    Cache getInstance(String name);
}
