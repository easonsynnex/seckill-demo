package com.eason.seckill.seckill.config.redis.keys;

/**
 * @Author: eason
 * @Date: Created in 16:51 2020/3/1
 * @Description:
 */
public class UsersKey extends BasePrefix {
    public static final int TOKEN_EXPIRE = 30 * 60;

    public UsersKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static final UsersKey TOKEN = new UsersKey(TOKEN_EXPIRE, "token");

}
