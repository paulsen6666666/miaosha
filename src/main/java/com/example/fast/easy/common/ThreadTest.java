package com.example.fast.easy.common;

/**
 * @author paul
 * @Date 2022/8/28 21:31
 */
public class ThreadTest {
    private static  Integer state = 0;

    private static  Object lock = new Object();

    private static  Integer count = 0;

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            while(true){

                synchronized (lock){
                    if(count <=100 && state==0){
                        System.out.println("A");
                        state = 1;
                        count++;
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }



                }
            }




        });


        Thread t2 = new Thread(() -> {
            while(true){

                synchronized (lock){
                    if(count <=100 && state==1){
                        System.out.println("B");
                        state = 2;
                        count++;
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }



                }
            }




        });


        Thread t3 = new Thread(() -> {
            while(true){

                synchronized (lock){
                    if(count <=100 && state==2){
                        System.out.println("C");
                        state = 0;
                        count++;
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }



                }
            }




        });

        t1.start();
        t2.start();
        t3.start();

    }
}
