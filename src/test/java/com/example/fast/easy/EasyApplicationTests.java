package com.example.fast.easy;

import com.example.fast.easy.dao.SeckillDao;
import com.example.fast.easy.entity.GmsGoods;
import com.example.fast.easy.entity.SeckillGoods;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class EasyApplicationTests {

    @Resource
    SeckillDao seckillDao;

    @Test
    void contextLoads() {
        SeckillGoods seckillGoods = seckillDao.selectStockById(1278943001673990144l);
        System.out.println(seckillGoods.getGoods());


    }



}
