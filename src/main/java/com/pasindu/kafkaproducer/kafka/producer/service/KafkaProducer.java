package com.pasindu.kafkaproducer.kafka.producer.service;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.scheduling.annotation.Async;

import java.io.Serializable;
import java.util.concurrent.Future;

public interface KafkaProducer <K extends Serializable, V extends SpecificRecordBase> {

    void send(String topicName, K key, V message);

}
