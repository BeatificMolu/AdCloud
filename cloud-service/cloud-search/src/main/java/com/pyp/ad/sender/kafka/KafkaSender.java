package com.pyp.ad.sender.kafka;

import com.alibaba.fastjson.JSON;
import com.pyp.ad.mysql.dto.MySqlRowData;
import com.pyp.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @description:
 * @author: yy
 * @data: Created in  2019/4/8 22:03
 * @modifier:
 * @version: V1.0
 */
@Component
@Slf4j
public class KafkaSender implements ISender {
    @Value("${adconf.kafa.topic}")
    private final KafkaTemplate<String, String> kafkaTemplate;
    private String topic;

    @Autowired
    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(MySqlRowData data) {
        kafkaTemplate.send(topic, JSON.toJSONString(data));
    }
    @KafkaListener(topics = {"kafka-sender"},groupId = "search")
    public void processMySqlRowData(ConsumerRecord<?, ?> record) {
        Optional<?> value = Optional.ofNullable(record.value());
        if (value.isPresent()) {
            Object message = value.get();
            MySqlRowData data = JSON.parseObject(message.toString(), MySqlRowData.class);
            System.out.println("kafka processMySqlRowData: " + JSON.toJSONString(data));
        }

    }
}
