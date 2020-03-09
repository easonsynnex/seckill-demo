package com.eason.seckill.seckill.service;

import com.alibaba.fastjson.JSONObject;
import com.eason.seckill.seckill.config.redis.RedisService;
import com.eason.seckill.seckill.config.redis.keys.GoodsKey;
import com.eason.seckill.seckill.dao.GoodsDao;
import com.eason.seckill.seckill.dao.OrderInfoDao;
import com.eason.seckill.seckill.entity.Good;
import com.eason.seckill.seckill.entity.OrderInfo;
import com.eason.seckill.seckill.entity.SeckillGood;
import com.eason.seckill.seckill.vo.GoodVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: eason
 * @Date: Created in 20:22 2020/2/23
 * @Description:
 */
@Service
public class OrderInfoService {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderInfoService.class);

    @Autowired
    OrderInfoDao orderInfoDao;

    public int saveOrderIndo(OrderInfo order){
        return orderInfoDao.saveOrderInfo(order);
    }
}
