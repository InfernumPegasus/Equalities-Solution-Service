package com.example.service.stats;

import com.example.service.logger.MyLogger;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Service
public class StatsProvider {

    private static List<Integer> roots = new ArrayList<>();

    private Stats stats;

    private static boolean shouldBeRecalculated = true;

    @Autowired
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }

    public void increaseTotalRequests() {
        stats.increaseTotalRequests();
    }

    public void increaseWrongRequests() {
        stats.increaseWrongRequests();
    }

    public void calculate() {

        MyLogger.log(Level.INFO, "Collecting stats...");

        if (shouldBeRecalculated) {
            stats.mostCommon = roots
                    .stream()
                    .reduce(
                            BinaryOperator.maxBy(Comparator.comparingInt(o -> Collections.frequency(roots, o)))
                    ).orElse(0);

            roots = roots
                    .stream()
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            stats.min = roots
                    .stream()
                    .min(Comparator.comparing(Long::valueOf))
                    .orElse(0);

            stats.max = roots
                    .stream()
                    .max(Comparator.comparing(Long::valueOf))
                    .orElse(0);

            MyLogger.log(Level.WARN, "StatsProvider recollected!");

            shouldBeRecalculated = false;
        }
    }

    public void addRoot(@NotNull Integer root) {
        roots.add(root);
        shouldBeRecalculated = true;
    }
}
