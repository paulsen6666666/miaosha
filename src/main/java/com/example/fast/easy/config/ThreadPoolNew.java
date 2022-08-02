package com.example.fast.easy.config;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;



/**
 * @author paul
 * @Date 2022/7/21 22:10
 */
@Configuration
@EnableAsync
public class ThreadPoolNew {
    private static final int MAX_POOL_SIZE = 100;
    private static final int CORE_POOL_SIZE = 50;
    private static final int TASK_NUM = 200;
    private static final int ACTIVE_TIME = 60;

    @Bean("asyncTaskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor asyncTaskExecutor = new ThreadPoolTaskExecutor();
        asyncTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        asyncTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        asyncTaskExecutor.setQueueCapacity(TASK_NUM);
        asyncTaskExecutor.setKeepAliveSeconds(ACTIVE_TIME);
        asyncTaskExecutor.setThreadNamePrefix("async-task-thread-pool");
        asyncTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        asyncTaskExecutor.initialize();
        return asyncTaskExecutor;
    }




}
