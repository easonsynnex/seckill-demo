package com.eason.seckill.seckill.config.redis;

import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @Author: eason
 * @Date: Created in 21:31 2020/2/23
 * @Description:
 */
@Configuration
@EnableCaching
public class RedisConfig {
    @Autowired
    private RedisConnectionFactory factory;

    @Autowired
    private RedisSerializer fastJson2JsonRedisSerializer;

    @Bean
    public RedisSerializer fastJson2JsonRedisSerializer() {
        //ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        //ParserConfig.getGlobalInstance().setAsmEnable(true);
        return new FastJson2JsonRedisSerializer<Object>(Object.class);
    }
    //fastjson
    @Bean(name="redisTemplate")
    public RedisTemplate<String, Object> fastJsonRedisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        //redis开启事务
        //template.setEnableTransactionSupport(true);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(fastJson2JsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(fastJson2JsonRedisSerializer);
        template.setDefaultSerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}