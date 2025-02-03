package by.clevertec.news.api.cache;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.Callable;

@Slf4j
@Getter
public class NewsServiceCacheLRU extends NewsServiceCache {

    private final Object lock = new Object();
    private final LinkedHashMap<UUID, Object> cache;

    public NewsServiceCacheLRU(String name, int limit) {
        super(name, limit);
        cache = new LinkedHashMap<>(limit);
    }

    @Override
    public ValueWrapper get(Object key) {

        Object value = getValue(key);
        log.info("get object {} from cache1", value);
        return value != null ? new SimpleValueWrapper(value) : null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Class<T> type) {
        Object value = getValue(key);
        log.info("get object {} from cache2", value);
        return value != null ? (T) value : null;
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object value = getValue(key);
        log.info("get object {} from cache3", value);
        return value != null ? (T) value : valueLoader.call();
    }

    @Override
    public void put(Object key, Object value) {

        synchronized (lock) {
            if (!cache.containsKey(key) && cache.size() >= limit) {
                UUID oldestKey = cache.keySet().iterator().next();
                evict(oldestKey);
            }
            cache.remove(key);
            cache.put((UUID) key, value);
            log.info("put object {} in cache", value);
        }
    }

    @Override
    public void evict(Object key) {

        synchronized (lock) {
            cache.remove(key);
        }
        log.info("evict object with key {} from cache", key);
    }

    @Override
    public void clear() {
        synchronized (lock) {
            cache.clear();
        }
    }

    private Object getValue(Object key) {
        Object value = null;
        synchronized (lock) {
            value = cache.remove(key);
            if (value == null) {
                return null;
            }
            cache.put((UUID) key, value);
        }
        return value;
    }
}
