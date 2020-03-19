package com.eason.seckill.seckill.kafka;

import com.eason.seckill.seckill.config.redis.RedisService;
import com.eason.seckill.seckill.service.SeckillService;
import com.eason.seckill.seckill.vo.SeckillMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaReceiver {
    private static final Logger logger =  LoggerFactory.getLogger(KafkaReceiver.class);

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    SeckillService seckillService;

    @KafkaListener(topics = {KafkaProvider.TOPIC_SECKILL})
    public void listen(ConsumerRecord<String, Object> record){
        SeckillMessage msg = RedisService.StringToBean(record.value(), SeckillMessage.class);
        if(msg != null){
            logger.info("consumer receive msg from provider :{}", msg);
        }
        seckillService.doSeckillToDb(msg);
    }
}
