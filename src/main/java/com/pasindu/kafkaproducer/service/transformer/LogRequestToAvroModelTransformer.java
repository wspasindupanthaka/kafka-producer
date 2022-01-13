package com.pasindu.kafkaproducer.service.transformer;

import com.pasindu.kafkaproducer.kafka.model.LogAvroModel;
import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;
import org.springframework.stereotype.Component;

@Component
public class LogRequestToAvroModelTransformer {

    public LogAvroModel getLogAvroModel(LogRequestModel logRequestModel) {
        return LogAvroModel.
                newBuilder()
                .setId(logRequestModel.getId())
                .setApplicationName(logRequestModel.getApplicationName())
                .setClassName(logRequestModel.getClassName())
                .setLogLevel(logRequestModel.getLogLevel())
                .setDate(logRequestModel.getDate())
                .setMessage(logRequestModel.getMessage())
                .setRequestId(logRequestModel.getRequestId())
                .setServiceName(logRequestModel.getServiceName())
                .setUserName(logRequestModel.getUserName())
                .build();
    }

}
