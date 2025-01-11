package by.clevertec.news.api.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class NewsServiceCacheManager extends AbstractCacheManager {

    private final CustomCacheFactory cacheFactory;

    private final List<Cache> caches = Collections.emptyList();

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return caches;
    }

    @Override
    protected Cache getMissingCache(String name) {
        return cacheFactory.getInstance(name);
    }

}
