package com.example.fast.easy.service.impl;

import com.example.fast.easy.service.ExcelWorkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

/**
 * @author paul
 * @Date 2022/7/21 18:49
 */
@Slf4j
@Service
@EnableAsync
public class ExcelWorkServiceImpl implements ExcelWorkService {





    @Async("asyncTaskExecutor")
    @Override
    public Future<List<ConcurrentHashMap<String, String>>> getWork(Workbook workbook) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "：开始调用异步业务");
        //初始化基础数据
       List<ConcurrentHashMap<String, String>> list =  new CopyOnWriteArrayList<>();

        ConcurrentHashMap<String, String> map;    //用于逐一存储表格的每一行数据

        try {
            //使用特定的表的名称来
            Sheet sheet = workbook.getSheetAt(0);
            //最大的行数
            int length = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < length; i++) {
                map = new ConcurrentHashMap();
                Row row = sheet.getRow(i);
                //System.out.println(Thread.currentThread().getName() + "：调用异步业务结束，耗时：" );
                for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                    map.put(String.valueOf(row.getCell(j).getNumericCellValue()),
                            String.valueOf(row.getCell(j).getNumericCellValue()));

                   // System.out.println(Thread.currentThread().getName() + "：调用异步业务结束，耗时：" );
                }
                list.add(map);


            }
        } catch (Exception e) {
            return null;
        }

        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "：调用异步业务结束，耗时：" + (endTime - startTime));


        return new AsyncResult<>(list);


    }

    @Override
    public Workbook turn(MultipartFile file) {
        //获取文件类型，即文件名后缀，通过获取文件名并用.分割文件名，并取用返回值的第二个下标1
        String fileType = file.getOriginalFilename().split("\\.")[1];
        Workbook workbook;
        try {
            if (fileType.equals("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (fileType.equals("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return workbook;
    }

    @Override
    public void printMsg() {
        System.out.println("###############sendMsg2##################");
        for (int i = 0; i < 3; i++) {
            System.out.println(i);
        }
        System.out.println("###############sendMsg3##################");

    }



}
