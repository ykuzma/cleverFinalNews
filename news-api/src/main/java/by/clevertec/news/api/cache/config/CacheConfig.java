package by.clevertec.news.api.cache.config;

import by.clevertec.news.api.cache.CustomCacheFactory;
import by.clevertec.news.api.cache.NewsCacheFactory;
import by.clevertec.news.api.cache.NewsServiceCacheManager;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
@EnableConfigurationProperties(NewsServiceCacheProperty.class)
public class CacheConfig {

    @Bean
    @Profile("dev")
    public CacheManager getCacheManager(CustomCacheFactory factory) {
        return new NewsServiceCacheManager(factory);
    }

    @Bean
    public CustomCacheFactory cacheFactory(NewsServiceCacheProperty property){
        return new NewsCacheFactory(property);
    }

    @Bean
    @Profile("prod")
    public CacheManager redisCacheManager() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule())
                .activateDefaultTyping(
                        objectMapper.getPolymorphicTypeValidator(),
                        ObjectMapper.DefaultTyping.NON_FINAL,
                        JsonTypeInfo.As.PROPERTY);
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                                new GenericJackson2JsonRedisSerializer(objectMapper)));

        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(cacheConfiguration);

        return builder.build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setPort(6379);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }
}
