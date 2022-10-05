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




        int[] weight = {1, 3, 4};
        int[] value = {15, 20, 30};
        int bagsize = 4;









    }

    public static void testweightbagproblem(int[] weight, int[] value, int bagsize){

        int dp [][]=new int[weight.length+1][bagsize+1];

        for(int i =0;i<dp[0].length;i++){
            dp[0][i] = 0;

        }

        for(int i = 0;i<dp.length;i++){
            dp[i][0]=0;
        }

        for(int i =1;i<dp.length;i++){
            for(int j = 1;j<dp[0].length;j++){
                if(j<weight[i-1]){
                    dp[i][j] = dp[i-1][j];

                }else {
                    dp[i][j] = Math.max(dp[i-1][j],dp[i-1][j-weight[i-1]]+value[i]);

                }

            }

        }

        //打印dp数组



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

    public int uniquePaths(int m, int n) {
        int dp [][]= new int[m][n];

        for(int i =0;i<m;i++){
            dp[i][0] = 1;

        }

        for(int i = 0;i<n;i++){
            dp[0][i] = 1;

        }


        for(int i =1;i<m;i++){
            for(int j = 1;j<m;j++){
                dp[i][j] = dp[i][j-1]+dp[i-1][j];

            }

        }
            return dp[m-1][n-1];

    }


    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int dp [][]= new int[obstacleGrid.length][obstacleGrid[0].length];

        for(int i =0;i<obstacleGrid.length;i++){
            if(obstacleGrid[0][i] == 1){
                break;

            }
            dp[0][i] = 1;
        }

        for(int i =0;i<obstacleGrid[0].length;i++){
            if(obstacleGrid[i][0] == 1){
                break;

            }
            dp[i][0] = 1;
        }

        for(int i =1;i<obstacleGrid.length;i++){
            for(int j =1;j<dp[0].length;j++){
                if(obstacleGrid[i][j] == 0){
                    dp[i][j] = dp[i][j-1]+dp[i-1][j];
                }

            }


        }

        return dp[dp.length-1][dp[0].length];

    }

    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int i =0;i<nums.length;i++){
            sum+=nums[i];

        }

        int target = sum/k;
        int backet []=new int[k+1];
        return backtrack(nums, 0, backet, k, target);


    }

    private boolean backtrack(int[] nums, int index, int[] bucket, int k, int target) {

        if(index == nums.length){
            for(int i =0;i<nums.length;i++){
                if(bucket[i] !=target){
                    return false;

                }

            }

        }

        for(int i =0;i<nums.length;i++){
            if(bucket[i]+nums[i]>target){
                continue;

            }

            if(backtrack(nums, index+1, bucket, k, target))return true;
            bucket[i]-=nums[i];
            return false;

        }
        return false;

    }




    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num:nums){
            sum+=num;

        }

        int target = sum/2;

        int dp [] = new int[target+1];

        for(int i =0;i<nums.length;i++){
            for(int j = target;j>nums[i];j--){
                dp[j] = Math.max(dp[j],dp[j-nums[i]]+nums[i]);



            }


        }


        return dp[target] == target;




    }

     public int rob(int[] nums) {
         if (nums == null || nums.length == 0) return 0;
         if (nums.length == 1) return nums[0];

         int dp [] = new int[nums.length];

         dp[0] = nums[0];
         dp[1] = Math.max(dp[0], nums[1]);

         for(int i = 2; i < nums.length; i++){
                 dp[i] = Math.max(dp[i-2]+nums[i],dp[i-1]);



         }
         return dp[nums.length];




    }


    public int rob(TreeNode root) {
        int [] result= robDouble(root);
        return Math.max(result[0],result[1]);


    }

    public int [] robDouble(TreeNode root){
        int reslut []=new int[2];

        if(root == null){
            return new int[]{0,0};

        }

        int [] left = robDouble(root.left);

        int [] right = robDouble(root.right);

        //偷

        reslut[0] = root.val+left[0]+right[0];

        reslut[1] =  Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return reslut;















    }



    public int maxProfit(int[] prices) {

        int dp [][] = new int[prices.length][2];

        dp[0][0] -= prices[0];
        dp[0][1] = 0;

        for(int i = 1 ;i<prices.length;i++){
            dp[i][0] = Math.max(dp[i-1][0],-prices[i]);
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0]+prices[i] );



        }

        return dp[prices.length-1][1];

    }
























}
 class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
          this.left = left;
          this.right = right;
      }
  }
