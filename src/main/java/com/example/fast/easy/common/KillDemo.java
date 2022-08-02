package com.example.fast.easy.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author paul
 * @Date 2022/7/26 23:35
 */
public class KillDemo {






    /*
    启动10个线程
    库存6个
    生成一个合并队列
    每个用户都能拿到自己的请求响应 */
    public static void main(String[] args) throws InterruptedException {


        ExecutorService executorService = Executors.newCachedThreadPool();
        KillDemo killDemo = new KillDemo();
        //这是一个线程
        killDemo.delete();
        //主线程
        Thread.sleep(2000);


        CountDownLatch  countDownLatch = new CountDownLatch(10);

        List<Future<Result>> lists = new ArrayList<>();

        //这是10个线程
        for(int i =0;i<10;i++){
            Long orderId = Long.reverse(i);
            Long userId  = Long.reverse(i);
            Future<Result> future = executorService.submit(()->{
                //这里的作用就是构造一个并发，本来是for循环，这样是穿行的，一个一个执行，如果加入了这个之后的话，就可以将10
                //个请求一起发射
                countDownLatch.countDown();
                countDownLatch.await(3000,TimeUnit.SECONDS);
                return killDemo.operate(new UserRequest(orderId,userId,1));


            });

            lists.add(future);

        }







        lists.forEach(list ->{
            try {
                Result result = list.get();
                System.out.println(Thread.currentThread().getName() + " 客户端请求响应 " + result);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });




    }




    //这个可以抽象成数据库的数据
    private Integer stoc = 6;

    //合并队列
    BlockingDeque<RequestPromise> deque = new LinkedBlockingDeque<>(10);

    //入队列
    public Result operate(UserRequest userRequest) throws InterruptedException {

        RequestPromise requestPromise = new RequestPromise(userRequest);
        /**
         *这里还是要使用，因为这个对象是放在队列里的，但是队列里的元素是有竞争的
         *队列是一个全局变量，并且出现极端情况，还没执行wait之后，还没执行notify
         *
         *
         */
        synchronized (requestPromise) {
            boolean enqueueSuccess = deque.offer(requestPromise);
            if (!enqueueSuccess) {
                return new Result(500, "系统繁忙");
            }
            try {
                // 进队列成功后阻塞 200ms
                /*
                * 这里比较坑，设置超时时间之后，你不知道是超时时间引起的，还是说notify唤醒的，暂时没有好的解决办法
                *等待超时以后也不会有异常，直接会向下执行，因此去判断result的结果是最合适的，因为哪怕等待超时，去判断结果之后也会
                * 发现是否是哪种情况引起的
                * */
                requestPromise.wait(200);
                // 等待超时不会抛出异常。结束时间之后，直接往下执行，返回null
                if (requestPromise.getResult() == null) {
                    return new Result(500, "等待超时");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return requestPromise.getResult();

    }

    //合并队列的请求，并且扣库存
    public void delete() {
       //新启动一个线
        new Thread(()->{
            System.out.println(Thread.currentThread().getName());
            List<RequestPromise> list = new ArrayList<>();
            while (true) {
                //这里需要注意
                if (deque.isEmpty()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                        continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //不使用while进行一个轮询是因为，防止内存泄露，直接锁定队列的长度，后加进来的，在下一批次处理
                for (int i = 0; i <=deque.size(); i++) {
                    //队列自动移除
                    list.add(deque.poll());

                }

                int sum = list.stream().mapToInt(e -> e.getUserRequest().getCount()).sum();

                if(sum <= stoc){
                    stoc-=sum;
                    list.forEach(requestPromise1 -> {
                        requestPromise1.setResult(new Result(200, "扣减库存成功"));
                        synchronized (requestPromise1){
                            requestPromise1.notify();
                        }
                    });
                    //细节让轮询继续
                    continue;

                }
                //库存不足
                    for(RequestPromise requestPromise1 :list){
                        int count  = requestPromise1.getUserRequest().getCount();
                        if(count<=stoc){
                            stoc-=count;
                            requestPromise1.setResult(new Result(200, "success"));
                            //这里等待着商议

                        }else {
                            requestPromise1.setResult(new Result(500, "fail"));
                            //这里等待着商议


                        }
                        synchronized (requestPromise1){
                            requestPromise1.notify();

                        }



                    }
                list.clear();



                }




        }).start();


    }








}

//这里主要可以用加锁的方式
@Data
class RequestPromise {
    private UserRequest userRequest;
    private Result result;

    public RequestPromise(UserRequest userRequest) {
        this.userRequest = userRequest;
    }
}



@Data
class Result{
    private int success;
    private String msg;

    public Result(int success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}


@Data
class UserRequest {
    private Long orderId;
    private Long userId;
    private Integer count;

    public UserRequest(Long orderId, Long userId, Integer count) {
        this.orderId = orderId;
        this.userId = userId;
        this.count = count;
    }


}
