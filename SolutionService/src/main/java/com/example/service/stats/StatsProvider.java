package com.example.service.stats;

import com.example.service.logger.MyLogger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * This Service provides statistics
 * for SolutionService: counts amount
 * of total and wrong requests,
 * min and max of roots and most
 * common from them.
 */
@Service
public class StatsProvider {

    /**
     * List of calculated roots.
     * Uses for getting stats.
     */
    private List<Integer> roots = new ArrayList<>();

    /**
     * Stats variable which used
     * to store all the stats.
     * Contained in package-private class.
     */
    private Stats stats;

    /**
     * Variable that indicates
     * whether stats should
     * be recollected.
     */
    private boolean shouldBeRecalculated = true;

    @Autowired
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }

    public void increaseTotalRequests() {
        stats.totalRequests++;
    }

    public void increaseWrongRequests() {
        stats.wrongRequests++;
    }

    /**
     * Recollects stats.
     * If stats need to be recollected
     * calculates min, max, most common
     * and will not recalculate them
     * until new root added to the list.
     */
    public void collect() {

        MyLogger.info("Collecting stats...");

        if (!shouldBeRecalculated) {
            MyLogger.warn("Stats need not to be recollected!");
            return;
        }

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

        MyLogger.warn("Stats recollected!");

        shouldBeRecalculated = false;
    }

    /**
     * Adds root to the list
     * and makes list available
     * to collecting stats again.
     * @param root value to be added
     */
    public void add(@NotNull Integer root) {
        roots.add(root);
        shouldBeRecalculated = true;
    }
}
