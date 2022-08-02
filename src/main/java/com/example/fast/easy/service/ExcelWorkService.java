package com.example.fast.easy.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * @author paul
 * @Date 2022/7/21 18:34
 */
@Service
public interface ExcelWorkService {

    //进行解析
    @Async("asyncServiceExecutor")
    Future<List<ConcurrentHashMap<String, String>>> getWork(Workbook workbook) throws Exception;

    //转化文件
    Workbook turn(MultipartFile file);

    @Async
    void printMsg();
}
