package com.example.service;

import com.example.service.process.Solution;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ServiceApplicationTests {

    @Test
    void isRootEqualToTen() {
        var root = new Solution(10, 20, 0, 1000)
                .getResponse()
                .getRoot();

        assertThat(root).isEqualTo(10);
    }

    @Test
    void whenBordersAreNull() {
        var solution = new Solution(10, 20, null, null);

        assertThat(solution.getLeftBorder()).isEqualTo(Integer.MIN_VALUE);
    }
}
