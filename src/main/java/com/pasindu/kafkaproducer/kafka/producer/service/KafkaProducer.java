package com.pasindu.kafkaproducer.kafka.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;

import java.io.Serializable;

public interface KafkaProducer <K extends Serializable, V extends String> {

    void send(String topicName, Long key, LogRequestModel logRequestModel);
}
