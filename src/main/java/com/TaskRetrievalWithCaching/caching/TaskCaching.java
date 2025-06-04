package com.TaskRetrievalWithCaching.caching;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class TaskCaching {
    @Bean
    public CacheManager cacheManager(){

        CaffeineCacheManager cacheManager = new CaffeineCacheManager("taskCache");

        cacheManager.setCaffeine(caffeineCacheBuilder());

        return cacheManager;
    }
    Caffeine<Object, Object> caffeineCacheBuilder() {

        return Caffeine.newBuilder()
                .initialCapacity(5)
                .maximumSize(5);
    }
}
