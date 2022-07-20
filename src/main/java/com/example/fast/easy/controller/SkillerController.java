package com.example.fast.easy.controller;

import com.example.fast.easy.common.RestResponse;
import com.example.fast.easy.model.dto.SeckillMsg;
import com.example.fast.easy.rabbit.OrderSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author paul
 * @Date 2022/7/16 0:14
 */

@Controller
public class SkillerController {

    @Resource
    OrderSender orderSender;

    @PostMapping("/seckill/miaosha")
    public RestResponse actionSeckill(@RequestParam("seckillId") Long seckillId,
                                      @RequestParam("memberId") Long memberId) {
        // 秒杀请求消息封装类
        SeckillMsg msg = new SeckillMsg(seckillId, memberId);

        //发送到mq
        orderSender.sendSeckillMsg(msg);

        return RestResponse.success("秒杀中",null);
    }

}
