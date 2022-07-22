import com.example.fast.easy.common.Constant;
import com.example.fast.easy.common.RestResponse;
import com.example.fast.easy.service.OmsOrderService;
import com.example.fast.easy.service.SeckillService;
import com.example.fast.easy.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*import com.example.fast.easy.common.Constant;
import com.example.fast.easy.common.RestResponse;
import com.example.fast.easy.service.OmsOrderService;
import com.example.fast.easy.service.SeckillService;
import com.example.fast.easy.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*package com.example.fast.easy.service.impl;

import com.example.fast.easy.common.Constant;
import com.example.fast.easy.common.RestResponse;
import com.example.fast.easy.service.OmsOrderService;
import com.example.fast.easy.service.SeckillService;
import com.example.fast.easy.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author paul
 * @Date 2022/7/16 0:43
 */
/*@Slf4j
@Service
public class OmsOrderRedisServiceImpl implements SeckillService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private OmsOrderService omsOrderService;

    @Resource
    private RedisUtil redisUtil;


    @Override
    public void seckill(Long seckillId, Long memberId) {

        // redis热库存之后进行减库存操作
        boolean isSuccess = updateSeckillStock(seckillId);
        if(isSuccess){
            log.info("秒杀成功");

        }else {
            log.info("秒杀失败");
            return;
        }

        omsOrderService.orderGenerate(seckillId, memberId);



    }

    @Override
    public RestResponse<Object> seckillResult(Long seckillId, Long memberId) {
        // 从缓存中获取秒杀结果
        Object seckillOrder = redisUtil.get(Constant.SECKILL_ORDER + seckillId + "_" + memberId);
        if (seckillOrder == null) {
            return RestResponse.success("秒杀失败", null);
        }
        return RestResponse.success("秒杀成功", null);
    }


    public boolean updateSeckillStock(Long seckillId){
        Object execute = redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                //监控
                operations.watch((K) String.valueOf(seckillId));

                //搜索
                while(true){
                    Integer stock = (Integer) operations.opsForValue().get(String.valueOf(seckillId));

                    if(stock == 0 && stock ==null){
                        return  false;

                    }

                    operations.multi();

                    operations.opsForValue().decrement((K) String.valueOf(seckillId));

                    List<Object> results= operations.exec();

                    if (results == null || results.isEmpty()) { // 事务提交失败，重试
                        continue;
                    } else {
                        return true; // 扣减库存成功
                    }


                }





            }
        });
        return (boolean)execute;

    }


}*/