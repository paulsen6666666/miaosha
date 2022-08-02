package com.example.fast.easy.controller;

import com.example.fast.easy.common.RestResponse;
import com.example.fast.easy.model.dto.SeckillMsg;
import com.example.fast.easy.service.ExcelWorkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author paul
 * @Date 2022/7/21 18:40
 */

@Controller
public class ExcelReceiveController {












    @Resource
    ExcelWorkService excelWorkService;

    @RequestMapping("/excel/receive")
    @ResponseBody
    public RestResponse receiveAndAnalysis(MultipartFile file) throws ExecutionException, InterruptedException {
        long before = System.currentTimeMillis();


        Future<List<ConcurrentHashMap<String, String>>> listFuture = null;

        try {
            //解析文件
            if (file != null && file.getSize() > 0) {
                Workbook workbook = excelWorkService.turn(file);
                long startTime = System.currentTimeMillis();
                //进行解析
                for(int i =0;i<50;i++){
                    Future<List<ConcurrentHashMap<String, String>>> listFutureTask =excelWorkService.getWork(workbook);
                    listFuture = listFutureTask;
                    long endTime = System.currentTimeMillis();
                   // System.out.println(Thread.currentThread().getName() + "主线程，耗时：" + (endTime - startTime));
                }

                long today = System.currentTimeMillis();
                System.out.println("结束wys" + (today - before));

                //Future设置超时时间
                for (int i = 0; i < listFuture.get(80,TimeUnit.SECONDS).size(); i++) {
                    Map<String, String> maphh = (Map<String, String>) listFuture.get().get(i);

                    Iterator<Map.Entry< String, String >> iterator = maphh.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, String> entry = iterator.next();
                        System.out.println(entry.getValue());
                    }


                }







            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RestResponse.success(22);
    }
    @RequestMapping("/printMsg")
    public ResponseBody printMsg(){
        System.out.println("###############sendMsg1##################");
        excelWorkService.printMsg();
        System.out.println("###############sendMsg4##################");
        return null;

    }



    }




