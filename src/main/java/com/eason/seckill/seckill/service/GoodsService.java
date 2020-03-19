package com.eason.seckill.seckill.service;

import com.alibaba.fastjson.JSONObject;
import com.eason.seckill.seckill.config.redis.keys.GoodsKey;
import com.eason.seckill.seckill.dao.GoodsDao;
import com.eason.seckill.seckill.entity.Good;
import com.eason.seckill.seckill.config.redis.RedisService;
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
public class GoodsService {
    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    GoodsDao goodsDao;
    @Autowired
    RedisService redisUtils;

    @Autowired
    RedisService redisService;

    public List<Good> getAllGoods(){
        List<Good> allGoods = goodsDao.getAllGoods();
        LOGGER.info("获取到所有的商品列表信息:{}", allGoods);
        redisUtils.set(GoodsKey.goodsList,"all_goods", allGoods);
        String goods = redisUtils.get(GoodsKey.goodsList,"all_goods", String.class);
        List<Good> good1 = JSONObject.parseArray(goods, Good.class);
        LOGGER.info("第一个商品名字:{}", good1.get(0).getGoodsName());

        return allGoods;
    }

    public List<GoodVo> getAllSeckillGoods() {
        return goodsDao.getAllSeckillGoods();
    }

    /**
     * 获取秒杀商品信息，缓存有则读缓存，无则读数据库
     * @param id
     * @return
     */
    public GoodVo getGoodDetailById(long id) {
        GoodVo seckillGoods = getSeckillGoodsFromRedis(id);
        if(seckillGoods == null){
            seckillGoods = goodsDao.getSeckillGoodById(id);
            addSeckillGoodToRedis(seckillGoods);
        }
        LOGGER.info("获取到的秒杀商品信息:"+seckillGoods.toString());
        //判断秒杀状态
        long now = System.currentTimeMillis();
        long start = seckillGoods.getStartDate().getTime();
        long end = seckillGoods.getEndDate().getTime();

        //未开始
        if(now < start){
            seckillGoods.setStatus(1);
        }else if(now > end){
            //已结束
            seckillGoods.setStatus(3);
        }else{
            //进行中
            seckillGoods.setStatus(2);
        }

        return seckillGoods;
    }

    /**
     * 从缓存中获取秒杀商品信息
     * @param id 商品ID
     * @return
     */
    public GoodVo getSeckillGoodsFromRedis(long id){
        GoodVo good = redisService.get(GoodsKey.good, String.valueOf(id), GoodVo.class);
        return good;
    }

    /**
     * 将秒杀商品信息放入缓存中
     * @param goodVo
     */
    public void addSeckillGoodToRedis(GoodVo goodVo){
        redisService.set(GoodsKey.good, String.valueOf(goodVo.getGoodsId()), goodVo);
    }

    public boolean subtractOneGood(SeckillGood seckillGood){
        return goodsDao.subtractOneGood(seckillGood) == 1;
    }
}
