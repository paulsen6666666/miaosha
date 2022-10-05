package com.example.fast.easy.common;

import org.apache.ibatis.annotations.Mapper;

import java.util.*;

/**
 * @author paul
 * @Date 2022/7/26 17:36
 */
public class Test3 {
    public int eraseOverlapIntervals(int[][] intervals) {

        //二维数组的比较
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] == b[0]) {
                    return a[1] - b[1];
                } else {
                    return a[0] - b[0];
                }
            }


        });

        int count = 0;
        int edge = Integer.MIN_VALUE;
        for (int i = 0; i < intervals.length; i++) {
            // 若上一个区间的右边界小于当前区间的左边界，说明无交集
            if (edge <= intervals[i][0]) {
                edge = intervals[i][1];
            } else {
                count++;
            }
        }

        return count;
    }


    public List<Integer> partitionLabels(String s) {
        List<Integer> list = new LinkedList<>();
        int[] edge = new int[26];
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            edge[i - 'a'] = i;

        }
        int index;
        int lasy = 0;
        for (int i = 0; i < s.length(); i++) {
            index = Math.max(i, edge[i]);
            if (i == index) {
                list.add(i - lasy);
                lasy = i;

            }

        }

        return list;


    }


    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] == b[0]) {
                    return a[1] - b[1];
                } else {
                    return a[0] - b[0];
                }
            }


        });

        List<int[]> newlist = new ArrayList<>();
        int left = intervals[0][0];
        int right = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {

            if (intervals[i][0] > right) {

                newlist.add(new int[]{left, right});
                left = intervals[i][0];
                right = intervals[i][1];

            } else {
                right = Math.max(right, intervals[i][1]);
            }

        }

        newlist.add(new int[]{left, right});
        return newlist.toArray(new int[newlist.size()][]);


    }


    public int monotoneIncreasingDigits(int n) {
        int start = 0;
        String[] arr = (String.valueOf(n) + "").split("");
        for (int i = arr.length - 1; i > 0; i--) {
            if (Integer.parseInt(arr[i]) < Integer.parseInt(arr[i - 1])) {
                arr[i - 1] = (Integer.parseInt(arr[i - 1]) - 1) + "";
                start = i;


            }

        }

        for (int i = 0; i < start; i++) {
            arr[i] = "9";
        }

        return Integer.parseInt(String.join("", arr));


    }


    public int maxProfit(int[] prices, int fee) {
        int min = prices[0];
        int result = 0;
        for (int i = 0; i < prices.length; i++) {

            if (prices[i] < min) {
                min = prices[i];
            }

            if (prices[i] > min && min + fee < prices[i]) {
                continue;

            }

            if (prices[i] > min) {
                result += prices[i] - min - fee;
                min = prices[i];

            }


        }
        return result;

    }







    public List<Integer> pp(String s) {
        List<Integer> list = new ArrayList<>();
        int []arr = new int[26];
        for(int i =0;i<s.toCharArray().length;i++){
            arr[s.toCharArray()[i]-'a'] = i;

        }
        int index = 0;
        int last = 0;



        for(int i = 0;i<s.toCharArray().length;i++){
            index =Math.max(index,arr[s.toCharArray()[i] -'a']);
            if(index == i)
                list.add(i-last);
                last = i;



        }
        return list;




    }

    public int maxProt(int[] prices, int fee) {
       int buy = prices[0]+fee;
       int sum =0;
       for(int i =0;i<prices.length;i++){
           if(prices[i] + fee <buy){
               buy = prices[i]+fee;

           }else if(prices[i] > buy){
                    sum+=prices[i] - buy;
                    //这里确实少减一次手续费
                    buy = prices[i];
           }

       }
       return sum;

    }

    public int minCostClimbingStairs(int[] cost) {

        int dp [] = new int[cost.length];

        dp[0] = 0;
        dp[1] = 0;


        for(int i =0;i<cost.length;i++){
            dp[i] = Math.min(dp[i-1]+cost[i-1],dp[i-2]+cost[i-2]);

        }
        return dp[cost.length];




    }

    public int numTrees(int n) {

        int dp [] = new int[n];

        dp[0] = 1;

        for(int i =1;i<n;i++){
            for(int j =0;i<=j;j++){
                dp[i] = dp[j-1]*dp[i-j];

            }

        }

        return dp[n];
    }

    public int[] nextGreaterElements(int[] nums) {

        //边界判断
        if(nums == null || nums.length <= 1) {
            return new int[]{-1};
        }

        if(nums == null || nums.length <= 1) {
            return new int[]{-1};
        }

        int twoNums []= new int[nums.length*2];
        for(int i =0;i< nums.length;i++){
            twoNums[i] = nums[i];
            for(int j =0;j<nums.length;j++){
                twoNums[i+nums.length]=nums[j];


            }
        }
        int size = nums.length;
        int[] result = new int[twoNums.length];//存放结果

        Arrays.fill(result,-1);//默认全部初始化为-1
        Stack<Integer> st= new Stack<>();//栈中存放的是nums中的元素下标

        for(int i =0;i<nums.length;i++){
            while(!st.isEmpty() && nums[i] > nums[st.peek()]){
                result[i] = st.peek();
                st.pop();

            }

            st.push(i);

        }
        //在循环一次呗。
        return result;




    }



    public int trap(int[] height) {
        int sum = 0;

        for(int i =0;i<height.length;i++){
            if(i == 0&& i == height.length-1){
                continue;

            }

            int rHeight = height[i]; // 记录右边柱子的最高高度
            int lHeight = height[i]; // 记录左边柱子的最高高度

            for (int r =i+1;r<height.length;r++){
                if(height[r]>rHeight){
                    rHeight = height[r];

                }

            }

            for(int l = i-1;l>0;l--){
                if(height[l] > lHeight){
                    lHeight = height[l];

                }

            }

            int h = Math.min(lHeight, rHeight) - height[i];
            if (h > 0) sum += h;








        }
        return 1;



    }


















}
