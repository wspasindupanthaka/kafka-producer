package com.pasindu.kafkaproducer.kafka.producer.service.impl;

import com.pasindu.kafkaproducer.kafka.model.LogAvroModel;
import com.pasindu.kafkaproducer.kafka.producer.service.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class LogsKafkaProducerTest {

    @Autowired
    private LogsKafkaProducer producer;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingtoSimpleProducer_thenMessageReceived() {
        producer.send("topic",0L, new LogAvroModel());
    }
}
