package com.eventsvk.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static com.eventsvk.constant.AsyncExecutorName.DEFAULT_EXECUTOR;
import static com.eventsvk.constant.AsyncExecutorName.VK_POST_OAUTH_SAVE;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    @Bean(name = DEFAULT_EXECUTOR)
    public Executor defaultExecutor() {
        return buildExecutor(DEFAULT_EXECUTOR);
    }

    @Bean(name = VK_POST_OAUTH_SAVE)
    public Executor vkPostAuthSaveExecutor() {
        return buildExecutor(VK_POST_OAUTH_SAVE);
    }

    @Override
    public Executor getAsyncExecutor() {
        return defaultExecutor();
    }

    private ThreadPoolTaskExecutor buildExecutor(String threadNamePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        return executor;
    }
}
