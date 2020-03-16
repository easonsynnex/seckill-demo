package com.eason.seckill.seckill.kafka;

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

    @KafkaListener(topics = {KafkaProvider.TOPIC_SECKILL})
    public void listen(ConsumerRecord<String, Object> record){
        Object value = record.value();
        if(value != null){
            logger.info("consumer receive msg from provider :{}", value);
        }
    }
}
