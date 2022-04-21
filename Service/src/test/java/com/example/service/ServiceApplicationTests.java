package com.example.service;

import com.example.service.process.InputParams;
import com.example.service.services.Solution;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.LinkedList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ServiceApplicationTests {

    public static AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(SpringConfig.class);

    private static final Solution solution =
            context.getBean("solution", Solution.class);

    @Test
    void isRootEqualToTen() {
        solution.calculateRoot(new InputParams(10, 20, null, null));

        assertThat(solution.getRoot()).isEqualTo(10);
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

    @Test
    void bulkOperationsCounter() {

        var params = new LinkedList<InputParams>();

        var s1 = new InputParams(10, 645, null, null);
        var s2 = new InputParams(10, 45, null, null);
        var s3 = new InputParams(56, 20, null, null);

        params.add(s1);
        params.add(s2);
        params.add(s3);

        var count = params
                .stream()
                .filter(p -> p.getFirstValue() >= 10 && p.getSecondValue() <= 1000)
                .count();

        assert count == params.size();

    }
}
