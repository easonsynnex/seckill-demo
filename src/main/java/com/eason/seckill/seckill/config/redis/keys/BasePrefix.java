package com.eason.seckill.seckill.config.redis.keys;

import com.eason.seckill.seckill.config.redis.keys.KeyPrefix;

/**
 * @Author: eason
 * @Date: Created in 18:36 2020/2/24
 * @Description:
 */
public abstract class BasePrefix implements KeyPrefix {
    private int expireSeconds;
    private String prefix;

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    /**
     * 0则不过期
     * @param prefix
     */
    public BasePrefix(String prefix) {
        this(0,prefix);
    }

    @Override
    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    /**
     * 通过具体的实现来拼装对应的前缀
     * @return
     */
    @Override
    public String getPrefix() {
        return getClass().getSimpleName() + ":" + prefix;
    }

}
