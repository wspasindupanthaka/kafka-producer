package com.pasindu.kafkaproducer.kafka.producer.service.impl;

import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;
import com.pasindu.kafkaproducer.kafka.producer.service.KafkaProducer;
import com.pasindu.kafkaproducer.util.JsonEncoder;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.Future;

@Service
public class LogsKafkaProducer implements KafkaProducer<Long, String> {

    private static final Logger LOG = LoggerFactory.getLogger(LogsKafkaProducer.class);

    private Producer<Long, String> producer;

    public LogsKafkaProducer(Producer<Long, String> producer) {
        this.producer = producer;
    }

    @Override
    public Future<RecordMetadata> send(String topicName, Long key, Object message) {

        LOG.info("Sending message='{}' to topic='{}'", message, topicName);
        ProducerRecord record = new ProducerRecord(topicName, key, message);
        Future<RecordMetadata> recordMetadataFuture = producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null)
                    e.printStackTrace();
            }
        });

        return recordMetadataFuture;
    }

}
