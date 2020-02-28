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
    public static final GoodsKey goodsList = new GoodsKey(60, "gl");

}
