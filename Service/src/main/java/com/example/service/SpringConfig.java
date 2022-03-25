package com.example.service;

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

}
