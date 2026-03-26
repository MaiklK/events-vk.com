package com.eventsvk.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static com.eventsvk.constant.AsyncExecutorName.SEARCH_EVENTS_EXECUTOR;
import static com.eventsvk.constant.AsyncExecutorName.VK_POST_OAUTH_SAVE;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    @Bean(name = SEARCH_EVENTS_EXECUTOR)
    public Executor searchEventsExecutor() {
        return buildExecutor(SEARCH_EVENTS_EXECUTOR);
    }

    @Bean(name = VK_POST_OAUTH_SAVE)
    public Executor vkPostAuthSaveExecutor() {
        return buildExecutor(VK_POST_OAUTH_SAVE);
    }

    private ThreadPoolTaskExecutor buildExecutor(String threadNamePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}
