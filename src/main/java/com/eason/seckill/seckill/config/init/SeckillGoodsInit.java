package com.eason.seckill.seckill.config.init;

import com.eason.seckill.seckill.config.redis.RedisService;
import com.eason.seckill.seckill.config.redis.keys.GoodsKey;
import com.eason.seckill.seckill.dao.GoodsDao;
import com.eason.seckill.seckill.vo.GoodVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 项目初始化的时候加载秒杀商品信息到redis
 */
@Component
public class SeckillGoodsInit implements InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(SeckillGoodsInit.class);
    @Autowired
    RedisService redisService;

    @Autowired
    GoodsDao goodsDao;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodVo> seckillGoods = goodsDao.getAllSeckillGoods();
        for (GoodVo good :
                seckillGoods) {
            logger.info("初始化秒杀商品:{} ,秒杀数量:{}", good.getGoodsName(), good.getGoodsStock());
            redisService.set(GoodsKey.seckillGoodsCount, "" + good.getId(), good.getStockCount());
        }
    }
}
