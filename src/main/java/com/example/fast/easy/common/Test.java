package com.example.fast.easy.common;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author paul
 * @Date 2022/7/20 23:38
 */
public class Test {

    private static volatile LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<Runnable>(5);

    public static volatile int count;


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 900; i++) {
            list.add("a" + i);
        }
        ExecutorService touchWorker = Executors.newFixedThreadPool(9, Executors.defaultThreadFactory());




        int size = list.size();
        long start = System.currentTimeMillis();
        if (size > 100) {
            int batch = size % 100 == 0 ? size / 100 : size / 100 + 1; //将List集合切片
            for (int j = 0; j < batch; j++) {
                int end = (j + 1) * 100;
                if (end > size) {
                    end = size;
                }
                List<String> subList = list.subList(j * 100, end);//截取每个小分片的数据 第一次是0-100的数据以此类推
                touchWorker.execute(() -> sleepTest(subList));//让线程池执行工作
            }
            touchWorker.shutdown();//关闭线程池
            while (true) {
                if (touchWorker.isTerminated()) {
                    break;
                }
            }
        } else {
            sleepTest(list);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000);
    }

    private static void sleepTest(List<String> subList) {
        List<Integer> list = new ArrayList<>();
        for (String i : subList) {
            try {
                //耗时操作
                System.out.println("######" + i + "######" + Thread.currentThread().getName());
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int maxSubArray(int[] nums) {
        int count = 0;//一个存当前
        int sum =0;//一个存累加
        for(int i =0;i<nums.length;i++){
            count+=nums[i];
            sum = Math.max(sum, count);

            if(count<0){
                count = 0;

            }



        }
        return sum;

    }


    public boolean canJump(int[] nums) {
        int result = 0;
        for(int i =0;i<nums.length;i++){
            result = Math.max(result, nums[i]+i);
            if(result>=nums.length){
                return true;

            }


        }
        return false;

    }

    public int jump(int[] nums) {
        int dangqiancover = 0;
        int count = 0;
        int xiayibudefugai =0;
        for(int i =0;i<nums.length;i++){
            xiayibudefugai = Math.max(xiayibudefugai,nums[i]+i);
            if(dangqiancover == i){
                count++;
                dangqiancover = xiayibudefugai;
                if(dangqiancover>=nums.length){
                    break;
                }


            }else {
                break;
            }

        }
        return count;

    }


    public int canCompleteCircuit(int[] gas, int[] cost) {
        int sum =0;
        int total = 0;
        int count = 0;
        for(int i =0;i<gas.length;i++){
            sum+=gas[i]-cost[i];
            total = gas[i]-cost[i];
            if(sum<0){
                count = i+1;
                sum =0;


            }
            if(total<0){
                return -1;
            }
        }
            return count;

    }





}

