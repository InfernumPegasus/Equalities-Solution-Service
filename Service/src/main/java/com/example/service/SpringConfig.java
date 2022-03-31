package com.example.service;

import com.example.service.async.Counter;
import com.example.service.cache.SolutionCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SpringConfig {

    @Bean("cache")
    @Scope(value = "singleton")
    SolutionCache cache() {
        return new SolutionCache();
    }

    @Bean("counter")
    @Scope(value = "singleton")
    Counter counterService() {
        return new Counter();
    }

}
