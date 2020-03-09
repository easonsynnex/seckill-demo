package com.eason.seckill.seckill.service;

import com.eason.seckill.seckill.config.redis.RedisService;
import com.eason.seckill.seckill.config.redis.keys.UsersKey;
import com.eason.seckill.seckill.dao.SeckillOrderDao;
import com.eason.seckill.seckill.entity.SeckillGood;
import com.eason.seckill.seckill.entity.SeckillOrder;
import com.eason.seckill.seckill.entity.User;
import com.eason.seckill.seckill.exception.GlobalException;
import com.eason.seckill.seckill.vo.GoodVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static com.eason.seckill.seckill.service.UserService.TOKEN_NAME;

/**
 * @Author: eason
 * @Date: Created in 21:07 2020/3/3
 * @Description: 秒杀订单表相关服务
 */
@Service
public class SeckillOrderService {

    @Autowired
    SeckillOrderDao seckillOrderDao;

    public boolean isSeckilled(long userId, long goodsId){
        return seckillOrderDao.getSeckillOrderByUserIdAndGoodsId(userId, goodsId) == 0;
    }

    public void saveSeckillOrder(SeckillOrder seckillOrder) {
        seckillOrderDao.saveSeckillOrder(seckillOrder);
    }
}
