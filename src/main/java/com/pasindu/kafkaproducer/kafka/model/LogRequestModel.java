package com.pasindu.kafkaproducer.kafka.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogRequestModel {

    private String id;
    private String className;
    private String date;
    private String message;
    private String logLevel;
    private String applicationName;
    private String serviceName;
    private String userName;
    private String requestId;

}
