package com.pasindu.kafkaproducer.kafka.producer.service.impl;

import com.pasindu.kafkaproducer.KafkaProducerApplication;
import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;
import com.pasindu.kafkaproducer.kafka.producer.service.KafkaProducer;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = KafkaProducerApplication.class)
@DirtiesContext
public class LogsKafkaProducerTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void givenKeyValue_whenSend_thenVerifyHistory() {

        MockProducer mockProducer = new MockProducer(true, new LongSerializer(), new StringSerializer());

        kafkaProducer = new LogsKafkaProducer(mockProducer);

        LogRequestModel logRequestModel = new LogRequestModel("MOCK_ID",
                "MOCK_CLASS",
                "2022-01-23",
                "MOCK_MESSAGE",
                "INFO",
                "MOCK_APPLICATION",
                "MOCK_SERVICE",
                "MOCK_USER",
                "MOCK_REQUEST_ID");

        kafkaProducer.send("MOCK_TOPIC", 0L, logRequestModel.toString());

        assertTrue(mockProducer.history().size() == 1);

    }
}
