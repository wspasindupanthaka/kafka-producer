package com.pasindu.kafkaproducer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pasindu.kafkaproducer.kafka.config.data.KafkaConfigData;
import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;
import com.pasindu.kafkaproducer.kafka.producer.service.KafkaProducer;
import com.pasindu.kafkaproducer.service.ProducerService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProducerServiceImpl implements ProducerService {

    private final KafkaProducer<Long, String> kafkaProducer;

    private final KafkaConfigData kafkaConfigData;

    public ProducerServiceImpl(KafkaProducer<Long, String> kafkaProducer, KafkaConfigData kafkaConfigData) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaConfigData = kafkaConfigData;
    }

    @Async("asyncConf")
    public void createLog(LogRequestModel logRequestModel) throws JsonProcessingException {
        kafkaProducer.send(kafkaConfigData.getTopicName(), 0L, logRequestModel);
    }


}
