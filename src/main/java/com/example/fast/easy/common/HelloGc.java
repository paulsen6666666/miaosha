package com.example.fast.easy.common;

import java.util.LinkedList;
import java.util.List;

/**
 * @author paul
 * @Date 2022/10/5 20:58
 */
public class HelloGc {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("hello gc");
        List list = new LinkedList();
        for(;;){
            byte [] bytes = new byte[1024*1024];
            list.add(bytes);

        }



    }
}
