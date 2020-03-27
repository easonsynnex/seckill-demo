package com.eason.seckill.seckill.config.redis.keys;

/**
 * @Author: eason
 * @Date: Created in 18:47 2020/2/24
 * @Description: 商品key
 */
public class GoodsKey extends BasePrefix {
    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public GoodsKey(String prefix) {
        super(prefix);
    }

    //所有商品
    public static final GoodsKey goodsList = new GoodsKey(60, "gl");
    //所有商品cache
    public static final GoodsKey goodsListCache = new GoodsKey(60, "glc");
    //商品
    public static final GoodsKey good = new GoodsKey(60, "good");
    //所有秒杀商品cache
    public static final GoodsKey seckillGoodsListCache = new GoodsKey(60, "sglc");
    //秒杀商品详情cache
    public static final GoodsKey seckillGoodsDetailCache = new GoodsKey(60, "sgdc");
    //秒杀商品数量
    public static final GoodsKey seckillGoodsCount = new GoodsKey("sgc");
    //秒杀结果
    public static final GoodsKey seckillGoodsResult = new GoodsKey("sgr");


}
