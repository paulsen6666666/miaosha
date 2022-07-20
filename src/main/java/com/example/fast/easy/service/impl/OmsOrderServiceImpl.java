package com.example.fast.easy.service.impl;

import com.example.fast.easy.common.Constant;
import com.example.fast.easy.common.RestResponse;
import com.example.fast.easy.dao.OmsOrderDao;
import com.example.fast.easy.dao.SeckillDao;
import com.example.fast.easy.entity.OmsOrder;
import com.example.fast.easy.entity.OmsOrderItem;
import com.example.fast.easy.entity.SeckillGoods;
import com.example.fast.easy.model.vo.OrderDetailsVo;
import com.example.fast.easy.rabbit.OrderSender;
import com.example.fast.easy.service.OmsOrderService;
import com.example.fast.easy.util.RedisUtil;
import com.example.fast.easy.util.WorkIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author paul
 * @Date 2022/7/19 17:56
 */
@Service
@Slf4j
public class OmsOrderServiceImpl implements OmsOrderService {
    @Autowired
    private OmsOrderDao omsOrderDao;
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    OrderSender orderSender;


    @Override
    public void orderGenerate(Long seckillId, Long memberId) {
        // 查询秒杀库存
        SeckillGoods seckillGoods = seckillDao.selectStockById(seckillId);
        // 获取当前秒杀商品的剩余库存量
        Integer stock = seckillGoods.getSeckillStock();
        if (stock == 0) {    // 秒杀库存不足
            log.info("库存不足！");
            return;
        }
        // 冻结库存（数据库层面）
        Integer integer = seckillDao.freezeStock(seckillId, 1);
        if (!(integer > 0)) {    // 库存冻结失败
            log.info("库存冻结失败！");
            return;
        }
        // 生成秒杀订单
        OrderDetailsVo orderDetails;    // 订单详情信息
        OmsOrder order = new OmsOrder();
        //全局统一id，使用的是雪花算法
        order.setId(WorkIdUtil.workId());
        order.setMemberId(memberId);
        order.setOrderType(2);
        order.setStatus(0);
        order.setCreateTime(new Date());
        OmsOrderItem orderItem = new OmsOrderItem();    // 订单行项目信息
        orderItem.setId(WorkIdUtil.workId());
        orderItem.setOrderId(order.getId());
        orderItem.setGoodsId(seckillId);
        orderItem.setGoodsName(seckillGoods.getGoods().getName());
        orderItem.setGoodsPrice(seckillGoods.getSeckillPrice());
        orderItem.setQuantity(1);
        orderItem.setCreateTime(new Date());
        // 总金额 = (商品单价 × 购买数量) => BigDecimal.multiply()
        order.setTotalAmount(orderItem.getGoodsPrice().multiply(new BigDecimal(orderItem.getQuantity())));

        // 添加订单
        omsOrderDao.addSeckillOrder(order);
        // 添加订单行项目
        omsOrderDao.addOrderItems(orderItem);



        // 订单详情存入缓存中，秒杀成功
        orderDetails = new OrderDetailsVo(order, Collections.singletonList(orderItem));

        //搞一个内存标记，key传过来的都是相同的，相同的请求打过来之后，可以做到直接查找redis
        redisUtil.set(Constant.SECKILL_ORDER + seckillId + "_" + memberId, orderDetails, 600);

        //发送到mq
        orderSender.ttlOrderCancelMsg(order.getId(), 60 * 1000);


        log.info("创建秒杀订单成功:{}",order.getId());



    }

    @Override
    public RestResponse<Object> orderCancel(Long orderId) {
        // 查询订单状态是否为未支付
        OmsOrder order = omsOrderDao.getOrderById(orderId);
        if (order == null || order.getStatus() != 0) {   // 订单已支付
            return RestResponse.success("订单已经支付",200);
        }
        // 取消超时订单->设置订单为交易取消状态 4 （关闭）
        omsOrderDao.cancelOrder(orderId,4);
        // 获取所有订单商品
        List<OmsOrderItem> orderItems = omsOrderDao.getOrderItemsByOrderId(orderId);
        // 解除订单商品的库存锁定
        seckillDao.unfreezeStock(orderItems);

        return RestResponse.success("超时订单取消成功",200);
    }


}
