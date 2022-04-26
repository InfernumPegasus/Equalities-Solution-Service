package com.example.service.stats;

import com.example.service.logger.MyLogger;
import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Getter
public class StatsProvider {

    private static List<Integer> roots = new ArrayList<>();

    private static boolean shouldBeRecalculated = true;

    public static void calculate(Stats stats) {

        MyLogger.log(Level.INFO, "Collecting stats...");

        if (shouldBeRecalculated) {
            stats.mostPopular = roots
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

    public static @NotNull HashMap<String, Long> calculateAndGet(Stats stats) {
        // calculating all stats
        calculate(stats);

        var response = new HashMap<String, Long>();

        response.put("TotalRequests", stats.totalRequests);
        response.put("WrongRequests", stats.wrongRequests);
        response.put("Min", stats.min.longValue());
        response.put("Max", stats.max.longValue());
        response.put("MostCommon", stats.mostPopular.longValue());

        return response;
    }

    public static void addRoot(@NotNull Integer root) {
        roots.add(root);
        shouldBeRecalculated = true;
    }
}
