package com.duytoan.imajicoffee.imaji_coffee_be.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineCatchConfig {
    private Caffeine<Object, Object> cacheSpec() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(1000);
    }
    @Bean
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Arrays.asList(
                new CaffeineCache("products", cacheSpec().build()),
                new CaffeineCache("news", cacheSpec().build()),
                new CaffeineCache("upcomingEvents", cacheSpec().build()),
                new CaffeineCache("closedEvents", cacheSpec().build())
        ));
        return manager;
    }
}
