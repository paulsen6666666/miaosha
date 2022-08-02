package com.example.fast.easy.watch;


import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: @极海Channel
 * @Date:2021/12/2
 * @Description:
 */
@SpringBootApplication
public class ObserverDemo implements CommandLineRunner {

    /** 这里可以注册多个观察者，这个demo只注册了一个消息通知 */
    @Autowired
    List<Observer> observerList;

    public static void main(String[] args) {
        new SpringApplication(ObserverDemo.class).run(args);
    }

    /** 容器启动即执行 */
    @Override
    public void run(String... args) {
        sendMsg("bizType1", "业务1的内容");
        sendMsg("bizType2", "业务2的内容");
    }
    /** client调用，指明业务和需要发送的内容 */
    private void sendMsg(String bizType, String content) {
        observerList.forEach(observer -> observer.notify(bizType, content));
    }
}

/**
 * 观察者，JDK自带有观察者，定义接口不指定实现
 */
interface Observer {

    void notify(String bizType, String content);
}

/**
 * 发送消息的观察者, 也实现了发送策略的组合和选择
 */
@Component
class SendMsgObserver1 implements Observer {

    /** 所有发送实现，均可见*/
    @Autowired
    private List<SendMsgService> sendMsgServices;

    @Override
    public void notify(String bizType, String content) {
        // 获取业务类型 -> 策略组合
        List<String> strategy = ConfigCenter.getStrategy(bizType);
        sendMsgServices.forEach(sendMsgService -> {
            // 配置的策略在内，发送
            if (strategy.contains(sendMsgService.getClass().getDeclaredAnnotation(Service.class).value())) {
                sendMsgService.sendMsg(content);
            }
        });
    }
}



@Component
class SendMsgObserver2 implements Observer {

    /** 所有发送实现，均可见*/
    @Autowired
    private List<SendMsgService> sendMsgServices;

    @Override
    public void notify(String bizType, String content) {
        // 获取业务类型 -> 策略组合
        List<String> strategy = ConfigCenter.getStrategy(bizType);
        sendMsgServices.forEach(sendMsgService -> {
            // 配置的策略在内，发送
            if (strategy.contains(sendMsgService.getClass().getDeclaredAnnotation(Service.class).value())) {
                sendMsgService.sendMsg(content);
            }
        });
    }
}







/**
 * 模拟配置中心, nacos/zookeeper/redis/MySQL等均可。
 */
class ConfigCenter {
    private static Map<String, String> sendMsgConfig = new HashMap<>();
    static {
        sendMsgConfig.put("bizType1", "email,sms");
        sendMsgConfig.put("bizType2", "sms");
    }
    // 根据业务获取配置，新业务，配置key-value即可
    public static List<String> getStrategy(String bizType) {
        return Lists.newArrayList(sendMsgConfig.get(bizType).split(","));
    }
}

/**
 * 发送消息的能力抽象，可以理解为策略接口
 */
interface SendMsgService {
    /** 发送内容*/
    void sendMsg(String content);
}

/**
 * 两种发送方式实现
 */
@Service("sms")
class SmsSendService implements SendMsgService {
    @Override
    public void sendMsg(String content) {
        System.out.println("短信发送:" + content);
    }
}
@Service("email")
class EmailSendService implements SendMsgService {
    @Override
    public void sendMsg(String content) {
        System.out.println("邮件发送:" + content);
    }
}