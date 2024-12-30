package by.clevertec.news.api.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class NewsServiceCacheManager extends AbstractCacheManager {

    private final List<Cache> caches = Collections.emptyList();

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return caches;
    }

    @Override
    protected Cache getMissingCache(String name) {
        return new NewsServiceCache(name);
    }
}
