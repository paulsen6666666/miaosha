package com.example.fast.easy.service;

import com.example.fast.easy.common.RestResponse;


/**
 * 秒杀活动Service
 */
public interface SeckillService {
    /**
     * 秒杀业务：
     *
     */
    void seckill(Long seckillId, Long memberId);

    /**
     * 验证秒杀结果
     * @param seckillId 秒杀商品id
     * @param memberId 秒杀会员id
     */
    RestResponse seckillResult(Long seckillId, Long memberId);
}
