package com.example.fast.easy.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Future;

/**
 * @author paul
 * @Date 2022/7/22 18:40
 */
public interface AsyncService {
    @Async
    void asyncTask();


    @Async
    Future<String> asyncTask(String s);


    @Async
    @Transactional
    void asyncTaskForTransaction(Boolean exFlg);
}
