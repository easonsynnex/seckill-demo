package com.eason.seckill.seckill.config.redis;

import com.alibaba.fastjson.JSON;
import com.eason.seckill.seckill.config.redis.keys.KeyPrefix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    public static <T> T StringToBean(Object val, Class<T> clazz) {
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

    /**
     * set 需要判断value类型
     * @param value
     * @param <T>
     * @return
     */
    private <T> Object beanToString(T value){
        Class<? extends Object> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class){
            return value;
        }else if(clazz == long.class || clazz == Long.class){
            return value;
        }else if(clazz == String.class){
            return (String)value;
        }else{
            //返回对象
            return JSON.toJSONString(value);
        }
    }

    public boolean set(KeyPrefix keyprefix, String key, Object value){
        key = assembleKey(keyprefix,key);
        Object val = beanToString(value);
        try{
            int expireSeconds = keyprefix.getExpireSeconds();
            if(expireSeconds > 0 ) {
                redisTemplate.opsForValue().set(key, val, keyprefix.getExpireSeconds(), TimeUnit.SECONDS);
            }else{
                redisTemplate.opsForValue().set(key, val);
            }
            return true;
        }catch (Exception e){
            LOGGER.error("Set value to Redis ERROR:{}", e);
            return false;
        }
    }

    private String assembleKey(KeyPrefix keyprefix ,String key){
        return keyprefix.getPrefix() + ":" + key;
    }

    public static void main(String[] args) {
        RedisService a = new RedisService();
        System.out.println(a.getClass().getSimpleName());
    }

    public Long desc(KeyPrefix keyPrefix, String key) {
        String realKey = assembleKey(keyPrefix, key);
        Long decrement = redisTemplate.opsForValue().increment(realKey, -1L);
        return decrement;
    }
    public Long incr(KeyPrefix keyPrefix, String key) {
        String realKey = assembleKey(keyPrefix, key);
        Long decrement = redisTemplate.opsForValue().increment(realKey, 1L);
        return decrement;
    }
}
