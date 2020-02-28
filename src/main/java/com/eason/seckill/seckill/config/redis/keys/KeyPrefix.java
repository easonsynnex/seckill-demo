package com.eason.seckill.seckill.config.redis.keys;

public interface KeyPrefix {
    /**
     * 过期时间
     * @return
     */
    int getExpireSeconds();

    /**
     * redis key的前缀
     * @return
     */
    String getPrefix();
}
