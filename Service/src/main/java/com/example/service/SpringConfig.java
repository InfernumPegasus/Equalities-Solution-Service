package com.example.service;

import com.example.service.async.Counter;
import com.example.service.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SpringConfig {

    @Bean("cache")
    @Scope(value = "singleton")
    Cache cache() {
        return new Cache();
    }

    @Bean("counter")
    @Scope(value = "singleton")
    Counter counterService() {
        return new Counter();
    }

}
