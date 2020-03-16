package com.eason.seckill.seckill.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;



/**
 * @Author: eason
 * @Date: Created in 21:22 2020/3/15
 * @Description: 消息发送
 */
@Component
public class KafkaProvider {
    private static final Logger logger =  LoggerFactory.getLogger(KafkaProvider.class);

    public static final String

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public void send(Object obj){
        kafkaTemplate.send()
    }
}
