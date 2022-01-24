package com.pasindu.kafkaproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;

public interface ProducerService {

    void createLog(LogRequestModel logRequestModel) throws JsonProcessingException;

}
