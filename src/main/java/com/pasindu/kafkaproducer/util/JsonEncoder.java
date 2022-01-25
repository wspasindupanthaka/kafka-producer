package com.pasindu.kafkaproducer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;
import org.springframework.stereotype.Component;

public class JsonEncoder {

    public static String encodeLogRequestModelToString(LogRequestModel logRequestModel) {
        String logRequestModelAsJson = null;
        try {
            logRequestModelAsJson = new ObjectMapper().writeValueAsString(logRequestModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return logRequestModelAsJson;
    }

}
