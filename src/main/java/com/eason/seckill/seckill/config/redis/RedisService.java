package com.eason.seckill.seckill.config.redis;

import com.alibaba.fastjson.JSON;
import com.eason.seckill.seckill.config.redis.keys.GoodsKey;
import com.eason.seckill.seckill.config.redis.keys.KeyPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: eason
 * @Date: Created in 22:00 2020/2/23
 * @Description:
 */
@Service
public class RedisService {
    private final static Logger LOGGER = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 从redis获取数据
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix keyprefix, String key, Class<T> clazz){
        key = assembleKey(keyprefix,key);
        Object val = redisTemplate.opsForValue().get(key);
        T t = null;
        try{
            t = StringToBean(val, clazz);
        }catch (Exception e){
            LOGGER.error("Get value from Redis ERROR:{}",e);
        }
        return t;
    }

    private <T> T StringToBean(Object val, Class<T> clazz) {
        String value = String.valueOf(val);
        if(clazz == int.class || clazz == Integer.class){
            return (T) Integer.valueOf(value);
        }else if(clazz == long.class || clazz == Long.class){
            return (T) Long.valueOf(value);
        }else if(clazz == boolean.class || clazz == Boolean.class){
            return (T) Boolean.valueOf(value);
        }else{
            //返回对象
            return JSON.parseObject(value, clazz);
        }
    }

    public boolean set(KeyPrefix keyprefix, String key, Object value){
        key = assembleKey(keyprefix,key);
        try{
            redisTemplate.opsForValue().set(key, JSON.toJSONString(value), keyprefix.getExpireSeconds(), TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            LOGGER.error("Set value to Redis ERROR:{}", e);
            return false;
        }
    }

    private String assembleKey(KeyPrefix keyprefix ,String key){
        return keyprefix.getClass().getSimpleName() + ":" + keyprefix.getPrefix() + ":" + key;
    }

    public static void main(String[] args) {
        RedisService a = new RedisService();
        System.out.println(a.getClass().getSimpleName());
    }

    public Long desc(KeyPrefix keyPrefix, String key) {
        String key = assembleKey(keyPrefix, key);
        return redisTemplate.opsForValue().decrement(key);
    }
}
