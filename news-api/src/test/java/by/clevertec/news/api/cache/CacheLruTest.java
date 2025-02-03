package by.clevertec.news.api.cache;

import by.clevertec.news.api.util.TestHelper;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;


class CacheLruTest extends CacheTest {

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("application.cache.strategy", () -> TestHelper.LRU);

    }

}
