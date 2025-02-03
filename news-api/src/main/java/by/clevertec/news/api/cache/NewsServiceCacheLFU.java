package by.clevertec.news.api.cache;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Getter
public class NewsServiceCacheLFU extends NewsServiceCache {

    private CacheNode head;

    private final ConcurrentMap<UUID, CacheNode> cache;
    private final ConcurrentMap<Integer, CacheNode> frequency;

    public NewsServiceCacheLFU(String name, int limit) {
        super(name, limit);
        cache = new ConcurrentHashMap<>(limit);
        frequency = new ConcurrentHashMap<>(limit);
    }

    @Override
    public ValueWrapper get(Object key) {
        CacheNode node = getCacheNode(key);
        return node != null ? new SimpleValueWrapper(node.value) : null;
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Class<T> type) {
        CacheNode node = getCacheNode(key);
        return node != null ? (T) node.value : null;
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        CacheNode node = getCacheNode(key);
        return node != null ? (T) node.value : valueLoader.call();
    }

    @Override
    public void put(Object key, Object value) {

        CacheNode node = cache.get(key);
        if (node == null) {

            if (cache.size() >= limit) {
                evictFromCache();
            }
            CacheNode newNode = new CacheNode((UUID) key, value, 0);
            cache.put((UUID) key, newNode);
            log.info("put object {} in cache", value);
            putFrequency(newNode);
        } else {
            node.value = value;
            incrementNodeFrequency(node);
        }

    }

    @Override
    public void evict(Object key) {
        CacheNode node = cache.remove(key);
        if(isTail(node)) {
            if(isHead(node)) {
                frequency.remove(node.frequency);
            } else {
                frequency.put(node.frequency, node.prev);
            }
        }
        if(node == head) {
            head = node.next;
        }
        node.unlink();
        log.info("evict object {} ", node.value);
    }

    @Override
    public void clear() {
        cache.clear();
        frequency.clear();
        head = null;
        log.info("clear cache {}", getName());
    }

    private CacheNode getCacheNode(Object key) {
        CacheNode node;
        if ((node = cache.get(key)) == null) {
            return null;
        }
        incrementNodeFrequency(node);
        log.info("get object {} from cache", node.value);
        return node;
    }

    private void putFrequency(CacheNode newNode) {
        CacheNode targetTail = frequency.put(0, newNode);
        if (targetTail == null) {
            if (head != null) {
                head.insertPrevious(newNode);
            }
            head = newNode;
        } else {
            targetTail.insertNext(newNode);
        }
    }

    private void evictFromCache() {
        CacheNode oldHead = head;
        head = head.next;
        if (isTail(oldHead)) {
            frequency.remove(oldHead.frequency);
        }
        oldHead.unlink();
        cache.remove(oldHead.key);
        log.info("evict object {} ", oldHead.value);
    }

    private boolean isTail(CacheNode oldHead) {
        return oldHead.next == null ||
                oldHead.next.frequency != oldHead.frequency;
    }

    void incrementNodeFrequency(CacheNode node) {
        int oldFrequency = node.frequency;
        int newFrequency = node.frequency + 1;
        CacheNode targetTail = frequency.put(newFrequency, node);
        if (isTail(node)) {
            if (isHead(node)) {
                frequency.remove(oldFrequency);
            } else {
                frequency.put(oldFrequency, node.prev);
            }
            if (targetTail != null) {
                if (node == head) {
                    head = node.next;
                }
                node.unlink();
                targetTail.insertNext(node);
            }
        } else {
            if (node == head) {
                head = node.next;
            }
            node.unlink();
            if (targetTail == null) {
                frequency.get(oldFrequency).insertNext(node);
            } else {
                targetTail.insertNext(node);
            }
        }
        node.frequency = newFrequency;
    }

    boolean isHead(CacheNode node) {
        return node.prev == null ||
                node.prev.frequency != node.frequency;
    }

    static class CacheNode {

        final UUID key;
        Object value;
        int frequency;
        CacheNode prev;
        CacheNode next;

        public CacheNode(UUID key, Object value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }


        public void unlink() {
            CacheNode p = prev;
            CacheNode n = next;
            if (p != null) {
                p.next = n;
                prev = null;
            }
            if (n != null) {
                n.prev = p;
                next = null;
            }
        }

        public void insertPrevious(CacheNode node) {
            node.prev = prev;
            node.next = this;
            if (prev != null) {
                prev.next = node;
            }
            prev = node;
        }

        public void insertNext(CacheNode node) {
            node.next = next;
            node.prev = this;
            if (next != null) {
                next.prev = node;
            }
            next = node;
        }


    }
}
