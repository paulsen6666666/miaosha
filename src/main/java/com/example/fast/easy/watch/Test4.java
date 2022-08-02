package com.example.fast.easy.watch;

import java.util.concurrent.CountDownLatch;

/**
 * @author paul
 * @Date 2022/7/28 22:44
 */
        public class Test4 {
            public static void main(String[] args) {
                //先搞一个公共的
                CountDownLatch c1 = new CountDownLatch(1);
                //在搞两个子线程
                CountDownLatch c2 = new CountDownLatch(2);

                for(int i=0; i<3; i++){
                    Thread thread = new Thread(new Player(c1,c2),String.valueOf(i));
                    thread.start();
                }


                try {
                    c1.countDown();//先去执行主线程
                    System.out.println("主线程+++start");
                    c2.await();//
                    System.out.println("主线程end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }



static class Player implements Runnable{
        CountDownLatch c1;
        CountDownLatch c2;

    public Player(CountDownLatch c1, CountDownLatch c2) {
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public void run() {
        try {

            c1.await();//因为此时已经为0了，所以不阻塞
            System.out.println(Thread.currentThread().getName()+"start");
            c2.countDown();
            System.out.println(Thread.currentThread().getName()+"end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }

    //首先主线程是1，然后两个线程是2，首先现在两个子线程地方阻塞一下，阻塞的是主线程，意思是子线程先不运行，然后主线程countdown（数量-1）一下，开始运行，之后
    //c2去await一下，因为子线程没运行还是2，所以可以阻塞住，把主线程给阻塞了，因为c1.await()的count已经是0l，主线程已经减少了，所以子线程可以运行，然后子线程运行之后
    //因为主线程有c2.await();也是0了，主线程最后继续执行




}


}




