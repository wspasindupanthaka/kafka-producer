package com.pasindu.kafkaproducer.kafka.producer.service.impl;

import com.pasindu.kafkaproducer.kafka.producer.service.KafkaProducer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderResult;

import java.util.concurrent.Future;

@Service
public class LogsKafkaProducer implements KafkaProducer<Long, String> {

    private static final Logger LOG = LoggerFactory.getLogger(LogsKafkaProducer.class);

    private KafkaSender<Long, String> kafkaSender;

    public LogsKafkaProducer(KafkaSender<Long, String> kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    @Override
    public void send(String topicName, Long key, String message) {
        LOG.info("Sending message='{}' to topic='{}'", message, topicName);
//        Flux<SenderResult<Object>> send = kafkaSender.send();
        System.out.println(kafkaSender);
    }

}
