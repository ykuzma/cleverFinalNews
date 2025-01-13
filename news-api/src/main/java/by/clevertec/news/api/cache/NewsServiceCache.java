package by.clevertec.news.api.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;

import java.util.Collections;

@RequiredArgsConstructor
public abstract class NewsServiceCache implements Cache {

    private final String name;
    protected final int limit;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return Collections.emptyMap();
    }


}
