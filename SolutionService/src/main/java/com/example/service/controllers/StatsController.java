package com.example.service.controllers;

import com.example.service.input.InputParams;
import com.example.service.services.CacheService;
import com.example.service.stats.Stats;
import com.example.service.stats.StatsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController {
    private final StatsProvider statsProvider;

    @Autowired
    public StatsController(StatsProvider statsProvider) {
        this.statsProvider = statsProvider;
    }

    @GetMapping
    public ResponseEntity<Stats> getAllStats() {
        statsProvider.calculate();
        return new ResponseEntity<>(statsProvider.getStats(), HttpStatus.OK);
    }

    @GetMapping("/cache")
    public ResponseEntity<Map<InputParams, Integer>> getCache() {
        return new ResponseEntity<>(CacheService.getSolutions(), HttpStatus.OK);
    }
}
