package com.pasindu.kafkaproducer.kafka.producer.service;

import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.Serializable;
import java.util.concurrent.Future;

public interface KafkaProducer <K extends Serializable, V extends String> {

    Future<RecordMetadata> send(String topicName, Long key, Object message);
}
