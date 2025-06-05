package com.TaskRetrievalWithCaching.caching;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LRUCacheConfig {

    @Bean
    public LRUCache<String, String> taskCache() {
        return new LRUCache<>(5);
    }
}
