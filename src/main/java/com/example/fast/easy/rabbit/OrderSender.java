package com.example.fast.easy.rabbit;

import com.example.fast.easy.config.RabbitConfig;
import com.example.fast.easy.model.dto.SeckillMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 */
@Slf4j
@Component
public class OrderSender {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送秒杀请求（削峰）
     * @param seckillMsg 秒杀业务参数 seckillId->秒杀商品id；memberId->会员用户id
     * @return
     */
    public void sendSeckillMsg(SeckillMsg seckillMsg) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_ORDER_DIRECT,
                RabbitConfig.QUEUE_ORDER_SECKILL, seckillMsg);
//        RestResponse result = (RestResponse) rabbitTemplate.convertSendAndReceive(QueueEnum.QUEUE_ORDER.getExchange(),
//                QueueEnum.QUEUE_ORDER.getRoutingKey(), seckillMsg);

//        assert result != null;
//        log.info(seckillMsg.getMemberId()+"：" + result.getMessage());
//        return result;
    }

//    /**
//     * 发送秒杀订单生成消息
//     */
//    public void sendGenerateOrderMsg(SeckillMsg seckillMsg) {
//
//        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_ORDER_DIRECT,
//                RabbitmqConfig.QUEUE_ORDER_GENERATE,seckillMsg);
//
//    }

    /**
     * 延迟队列（死信队列）
     * @param orderId 秒杀订单id
     * @param delayTimes 消息延迟毫秒值
     */
    @Async
    public void ttlOrderCancelMsg(Long orderId ,final long delayTimes) {
        // 给延迟队列发送消息
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_TTL_ORDER_DIRECT,
                RabbitConfig.QUEUE_TTL_ORDER,orderId,message -> {

                    message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                    return message;
                });
    }


    /**
     * 首先生产者发送一个带时间的消息给普通队列（普通队列特殊在于有一个绑定的死信队列），
     * 当消息时间到了，就会发送到死信队列，然后消费者消费的是死信队列的消息。
     * 死信队列和普通队列一样，只不过死信队列被绑定在普通队列上了。
     *
     *
     *
     */



}
