package com.example.fast.easy;

import com.example.fast.easy.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Future;

@SpringBootApplication
@EnableAsync

public class EasyApplication {

    /*@Autowired
    private AsyncService asyncService;*/


    public static void main(String[] args) {
        SpringApplication.run(EasyApplication.class, args);
    }

    /*@Bean
    public ApplicationRunner applicationRunner() {
        return applicationArguments -> {
            long startTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "：开始调用异步业务");

            // 无返回值
            asyncService.asyncTask();

            // 有返回值--主线程未使用到
            Future<String> asyncTask = asyncService.asyncTask("666");
            // 有返回值--主线程使用到
            System.out.println(Thread.currentThread().getName() + "：返回值：" + asyncTask.get());

            // 模拟事务异常回滚
            asyncService.asyncTaskForTransaction(true);

            long endTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "：调用异步业务结束，耗时：" + (endTime - startTime));
        };

    }*/

}



