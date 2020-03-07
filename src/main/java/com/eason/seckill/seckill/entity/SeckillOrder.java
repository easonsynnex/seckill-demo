package com.eason.seckill.seckill.entity;

/**
 * @Author: eason
 * @Date: Created in 22:06 2020/3/3
 * @Description: `miaosha_order`表
 */
public class SeckillOrder {
    long id;
    long userId;
    long orderId;
    long goodsId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
