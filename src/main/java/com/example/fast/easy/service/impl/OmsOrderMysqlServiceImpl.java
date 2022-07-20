package com.example.fast.easy.service.impl;

import com.example.fast.easy.common.RestResponse;
import com.example.fast.easy.dao.SeckillDao;
import com.example.fast.easy.entity.SeckillGoods;
import com.example.fast.easy.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author paul
 * @Date 2022/7/19 17:02
 */

@Service
@Slf4j
public class OmsOrderMysqlServiceImpl implements SeckillService {

    @Resource
    private SeckillDao seckillDao;

    @Override
    public void seckill(Long seckillId, Long memberId) {

        if(updateSeckillStock(seckillId)){
            log.info("秒杀成功");
        }else {
            log.info("秒杀失败");
        }

    }

    @Override
    public RestResponse seckillResult(Long seckillId, Long memberId) {
        return null;
    }

    public synchronized boolean updateSeckillStock(Long seckillId){
        //查询秒杀库存
        SeckillGoods seckillGoods = seckillDao.selectStockById(seckillId);

        if(seckillGoods.getGoods().getStock()<0){
            return false;


        }

        //执行秒杀
        return seckillDao.freezeStock(seckillId, 1)==1;




    }

}
