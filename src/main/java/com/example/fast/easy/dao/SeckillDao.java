package com.example.fast.easy.dao;


import com.example.fast.easy.entity.GmsGoods;
import com.example.fast.easy.entity.OmsOrderItem;
import com.example.fast.easy.entity.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 库存相关操作
 */
@Repository
@Mapper
public interface SeckillDao {
    /**
     * 查询秒杀库存
     */
    SeckillGoods selectStockById(Long seckillId);

    /**
     * 冻结库存（实际库存 - 商品下单数量）
     */
    Integer freezeStock(@Param("seckillId") Long seckillId,
                        @Param("quantity") Integer quantity);

    /**
     * 解除取消订单的库存锁定
     */
    Integer unfreezeStock(@Param("itemList") List<OmsOrderItem> itemList);
}
