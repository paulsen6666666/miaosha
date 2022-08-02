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










}
