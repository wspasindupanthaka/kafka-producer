package com.pasindu.kafkaproducer.service.impl;

import com.pasindu.kafkaproducer.kafka.config.data.KafkaConfigData;
import com.pasindu.kafkaproducer.kafka.model.LogAvroModel;
import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;
import com.pasindu.kafkaproducer.kafka.producer.service.KafkaProducer;
import com.pasindu.kafkaproducer.service.LogService;
import com.pasindu.kafkaproducer.service.transformer.LogRequestToAvroModelTransformer;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    private final LogRequestToAvroModelTransformer logRequestToAvroModelTransformer;

    private final KafkaProducer<Long, LogAvroModel> kafkaProducer;

    private final KafkaConfigData kafkaConfigData;

    public LogServiceImpl(KafkaProducer<Long, LogAvroModel> kafkaProducer, KafkaConfigData kafkaConfigData,
                          LogRequestToAvroModelTransformer logRequestToAvroModelTransformer) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaConfigData = kafkaConfigData;
        this.logRequestToAvroModelTransformer = logRequestToAvroModelTransformer;
    }

    @Override
    public void createLog(LogRequestModel logRequestModel) {
        LogAvroModel logAvroModel = logRequestToAvroModelTransformer.getLogAvroModel(logRequestModel);
        kafkaProducer.send(kafkaConfigData.getTopicName(), 0L, logAvroModel);
    }

    @Override
    public void createLogSync(LogRequestModel logRequestModel) {
        LogAvroModel logAvroModel = logRequestToAvroModelTransformer.getLogAvroModel(logRequestModel);
        kafkaProducer.sendSync(kafkaConfigData.getTopicName(), 0L, logAvroModel);
    }
}
