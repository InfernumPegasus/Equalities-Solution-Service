package com.example.service.controllers;

import com.example.service.services.CacheService;
import com.example.service.stats.Stats;
import com.example.service.stats.StatsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {
    private final StatsProvider statsProvider;

    @Autowired
    public StatsController(StatsProvider statsProvider) {
        this.statsProvider = statsProvider;
    }

    @GetMapping
    public Stats getAllStats() {
        statsProvider.calculate();
        return statsProvider.getStats();
    }

    @GetMapping("/cache")
    public ResponseEntity<String> printCache() {
        return new ResponseEntity<>(CacheService.getStringCache(), HttpStatus.OK);
    }
}
