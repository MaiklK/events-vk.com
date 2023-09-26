package com.eventsvk;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Test
    public void someCheck() {
        int a = 10;
        int b = 10;
        int c = a + b;

        assert c >= a : "проверка провалена";
        assert c != b : "проверка провалена";
    }


}
