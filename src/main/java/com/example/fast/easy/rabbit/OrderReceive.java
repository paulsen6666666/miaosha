package com.example.fast.easy.rabbit;

import com.example.fast.easy.config.RabbitConfig;
import com.example.fast.easy.model.dto.SeckillMsg;
import com.example.fast.easy.service.OmsOrderService;
import com.example.fast.easy.service.SeckillService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author paul
 * @Date 2022/7/20 17:02
 */
@Slf4j
@Component
public class OrderReceive {
   // @Qualifier("omsOrderRedisServiceImpl")
    //@Autowired
    //private SeckillService seckillService;

    @Autowired
    private OmsOrderService omsOrderService;
    /**
     * 消费秒杀请求
     * @param seckillMsg
     * @param msg
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_ORDER_SECKILL)
    public void receiveSeckillMsg(SeckillMsg seckillMsg, Message msg, Channel channel) throws IOException {
        log.info("TAG:{}",String.valueOf(msg.getMessageProperties().getDeliveryTag()));
        // 消费者消费  执行秒杀请求
        //seckillService.seckill(seckillMsg.getSeckillId(),seckillMsg.getMemberId());
        // 手动消息确认

//        channel.basicAck(msg.getMessageProperties().getDeliveryTag(),true);
//        return null;
    }

//    /**
//     * 消费生成秒杀订单消息
//     * @param seckillMsg
//     * @param msg
//     */
//    @RabbitListener(queues = RabbitmqConfig.QUEUE_ORDER_GENERATE)
//    public void receiveOrderGenerateMsg(SeckillMsg seckillMsg, Message msg) {
//
//        omsOrderService.orderGenerate(seckillMsg.getSeckillId(),seckillMsg.getMemberId());
//    }

    /**
     * 超时订单自动取消消息监听
     * @param orderId
     * @param msg
     */
    @RabbitListener(queues = RabbitConfig.QUEUE_ORDER_CANCEL)
    public void receiveOrderCancelMsg(Long orderId, Message msg,Channel channel) {
        log.info("超时订单取消:{}",orderId);
        // 调用超时订单取消业务
        omsOrderService.orderCancel(orderId);

    }
}
