package com.example.fast.easy.common;

import java.util.Arrays;

/**
 * @author paul
 * @Date 2022/7/25 21:59
 */

    public class QuickSort {
        public static void sort(int[] array, int low, int high) {
            if (low < high) {
                int part = partition(array, low, high);//获取中间索引，将区间一分为二，分为两个子区间
                sort(array, low, part - 1);//对前面的子区间快速排序
                sort(array, part + 1, high);//对后面的子区间快速排序
            }
        }
        //一趟快速排序，返回值是本次基准的最终索引位置
        private static int partition(int[] array, int low, int high) {
            int benchmark = array[low];//初始化基准，不妨将低位索引值赋给基准
            //从数组的两端向中间开始扫描，寻找基准元素位置
            while (low < high) {
                //高位指针开始向中间寻找比基准小的元素
                while ((low < high) && array[high] >= benchmark) {
                    high--;
                }
                //比基准小的高位索引元素赋值到低位索引
                if (low < high) {
                    array[low] = array[high];
                }
                //低位指针开始向中间寻找比基准大的元素
                while ((low < high) && (array[low] <= benchmark)) {
                    low++;
                }
                //比基准大的低位索引元素赋值到高位索引
                if (low < high) {
                    array[high] = array[low];
                }
            }
            //将基准元素归位，基准元素的索引位置就是两个索引指针相遇的位置
            array[low] = benchmark;
            return low;//返回基准元素的最终索引
        }


    public static void main(String[] args) {
        int[] array = new int[]{46,36,96,26,86,16,76,-17};
        int low = 0;//初始低位索引
        int high = array.length-1;//初始高位索引
        System.out.print("排序前：");
        System.out.println(Arrays.toString(array));
        //使用快速排序算法对数组排序
        QuickSort.sort(array,low,high);
        System.out.print("排序后：");
        System.out.println(Arrays.toString(array));

    }

    }
