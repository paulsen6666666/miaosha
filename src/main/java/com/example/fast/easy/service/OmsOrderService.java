package com.example.fast.easy.service;

import com.example.fast.easy.common.RestResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单模块 Service
 */
public interface OmsOrderService {

    /**
     * 秒杀订单生成
     */
    @Transactional(rollbackFor = Throwable.class)
    void orderGenerate(Long seckillId, Long memberId);

    /**
     * 超时订单取消关闭
     */
    RestResponse<Object> orderCancel(Long orderId);
}
