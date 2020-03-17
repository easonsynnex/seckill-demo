package com.eason.seckill.seckill.vo;

import com.eason.seckill.seckill.entity.User;

public class SeckillMessage {
    private User user;
    private Long goodId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }
}
