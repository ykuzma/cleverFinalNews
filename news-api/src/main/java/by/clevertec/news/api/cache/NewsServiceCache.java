package by.clevertec.news.api.cache;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RequiredArgsConstructor
public class NewsServiceCache implements Cache {

    private final String name;
    private final ConcurrentMap<UUID, Object> cache = new ConcurrentHashMap<>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return Collections.emptyMap();
    }

    @Override
    public ValueWrapper get(Object key) {

        Object value = cache.get(key);
        return value != null ? new SimpleValueWrapper(value) : null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
         Object value = cache.get(key);
        return value != null ? (T) value : null;
    }

    @SneakyThrows
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object value = cache.get(key);
        return value != null ? (T) value : valueLoader.call();
    }

    @Override
    public void put(Object key, Object value) {
        cache.put((UUID) key, value);
    }

    @Override
    public void evict(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }
}
