package com.eventsvk.util;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class TimeUtils {
    @Value("${pause_between_request}")
    private static int PAUSE_BETWEEN_REQUEST;

    @SneakyThrows
    public static void pauseRequest() {
        Thread.sleep(PAUSE_BETWEEN_REQUEST);
    }
}
