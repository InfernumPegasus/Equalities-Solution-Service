package com.example.service.cache;

import com.example.service.logger.MyLogger;
import com.example.service.input.InputParams;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service provides in-memory cache
 */
@Service
public class CacheService {
    private final Map<InputParams, Integer> solutions = new ConcurrentHashMap<>();

    /**
     * Adds pair <{@link InputParams}, {@link Integer}> to cache
     * @param params key
     * @param root value
     */
    public void add(@NotNull InputParams params,
                    @NotNull Integer root) {
        if (!solutions.containsKey(params)) {
            solutions.put(params, root);
            var message = """
            Value %s@root=%d added to cache!
            """.formatted(params, root);

            MyLogger.log(Level.INFO, message);
        }
    }

    /**
     * Checks whether cache contains
     * {@link InputParams} value
     * @param value value to check
     * @return true if exists, false otherwise
     */
    public boolean contains(InputParams value) {
        return solutions.containsKey(value);
    }

    /**
     * Gets {@link Integer} value by
     * {@link InputParams} key
     * @param params key
     * @return found value, null otherwise
     */
    public Integer get(InputParams params) {
        if (solutions.containsKey(params)) {
            MyLogger.log(Level.WARN, "Root for " + params + " found in cache!");
            return solutions.get(params);
        }
        MyLogger.log(Level.WARN, "Root for " + params + " was not found in cache!");
        return null;
    }

    public Map<InputParams, Integer> getSolutions() {
        return solutions;
    }

    @PreDestroy
    void preDestroy() {
        solutions.clear();
        MyLogger.log(Level.WARN, "Cache: destroyed!");
    }
}