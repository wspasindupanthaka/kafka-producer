package com.pasindu.kafkaproducer.service;

import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;

public interface LogService {

    void createLog(LogRequestModel logRequestModel);

}
