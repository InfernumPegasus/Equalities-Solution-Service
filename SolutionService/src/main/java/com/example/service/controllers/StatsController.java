package com.example.service.controllers;

import com.example.service.input.InputParams;
import com.example.service.cache.CacheService;
import com.example.service.stats.Stats;
import com.example.service.stats.StatsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * REST Controller for
 * getting stats.
 */
@RestController
@RequestMapping("/stats")
public class StatsController {

    private final StatsProvider statsProvider;

    private final CacheService cacheService;

    @Autowired
    public StatsController(StatsProvider statsProvider, CacheService cacheService) {
        this.statsProvider = statsProvider;
        this.cacheService = cacheService;
    }

    /**
     * Method which is used to
     * calculate and get {@link Stats} stats
     * @return {@link ResponseEntity} with stats and status code OK if success
     */
    @GetMapping
    public ResponseEntity<Stats> getAllStats() {
        statsProvider.collect();
        return new ResponseEntity<>(statsProvider.getStats(), HttpStatus.OK);
    }

    /**
     * Returns cached unique solutions
     * @return {@link Map}<{@link InputParams}, {@link Integer}>> cached params
     */
    @GetMapping("/cache")
    public ResponseEntity<Map<InputParams, Integer>> getCache() {
        return new ResponseEntity<>(cacheService.getSolutions(), HttpStatus.OK);
    }
}
