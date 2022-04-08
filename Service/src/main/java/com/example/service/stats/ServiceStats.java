package com.example.service.stats;

import com.example.service.logger.MyLogger;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Getter
public class ServiceStats {

    private static List<Integer> roots = new ArrayList<>();

    private static Long totalRequests = 0L;
    private static Long wrongRequests = 0L;

    @Setter private static Integer min = 0;
    @Setter private static Integer max = 0;

    @Setter private static Integer mostPopular = 0;

    public static void increaseTotalRequests() {
        totalRequests++;
    }

    public static void increaseWrongRequests() {
        wrongRequests++;
    }

    private static void countAllParams() {

        MyLogger.log(Level.INFO, "Collecting stats...");

        mostPopular = roots
                .stream()
                .reduce(
                        BinaryOperator.maxBy(Comparator.comparingInt(o -> Collections.frequency(roots, o)))
                ).orElseThrow(IllegalStateException::new);

        roots = roots
                .stream()
                .distinct()
                .collect(Collectors.toList());

        min = roots
                .stream()
                .min(Comparator.comparing(Long::valueOf))
                .orElseThrow(IllegalStateException::new);

        max = roots
                .stream()
                .max(Comparator.comparing(Long::valueOf))
                .orElseThrow(IllegalStateException::new);

    }

    public static @NotNull Map<String, Long> getStats() {
        countAllParams();

        var stats = new HashMap<String, Long>();

        stats.put("TotalRequests", totalRequests);
        stats.put("WrongRequests", wrongRequests);
        stats.put("Min", min.longValue());
        stats.put("Max", max.longValue());
        stats.put("MostCommon", mostPopular.longValue());

        return stats;
    }

    public static void add(@NotNull Integer root) {
        roots.add(root);
    }
}
