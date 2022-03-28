package com.example.service;

import com.example.service.process.InputParams;
import com.example.service.process.Solution;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ServiceApplicationTests {

    @Test
    void isRootEqualToTen() {
        Solution.calculateRoot(new InputParams(10, 20, 0, 1000));

        assertThat(Solution.getRoot()).isEqualTo(10);
    }

    @Test
    void areTwoSameObjectsEqual() {
        var s1 = new InputParams(10, 20, 199, 399);
        var s2 = new InputParams(10, 20, 199, 399);

        assertThat(s1.equals(s2)).isTrue();
    }

    @Test
    void areTwoSameObjectsHaveSameHash() {
        var s1 = new InputParams(10, 20, 199, 399);
        var s2 = new InputParams(10, 20, 199, 399);

        assert s1.hashCode() == s2.hashCode();
    }

    @Test
    void isLeftBorderCorrectIfNull() {
        var params = new InputParams(10, 20, null, 1000);

        assertThat(params.getLeftBorder()).isEqualTo(Integer.MIN_VALUE);
    }
}
