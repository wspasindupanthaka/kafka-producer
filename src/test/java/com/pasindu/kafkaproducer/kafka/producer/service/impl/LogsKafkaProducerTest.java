package com.pasindu.kafkaproducer.kafka.producer.service.impl;

import com.pasindu.kafkaproducer.KafkaProducerApplication;
import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;
import com.pasindu.kafkaproducer.kafka.producer.service.KafkaProducer;
import com.pasindu.kafkaproducer.util.JsonEncoder;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertThrows;
import static reactor.core.publisher.Signal.isError;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = KafkaProducerApplication.class)
@DirtiesContext
public class LogsKafkaProducerTest {

    @Autowired
    private KafkaProducer kafkaProducer;

    private static LogRequestModel logRequestModel;

    private static MockProducer mockProducer;

    private static final String TOPIC = "MOCK_TOPIC";

    @BeforeAll
    public static void init() {
        logRequestModel = new LogRequestModel("MOCK_ID",
                "MOCK_CLASS",
                "2022-01-23",
                "MOCK_MESSAGE",
                "INFO",
                "MOCK_APPLICATION",
                "MOCK_SERVICE",
                "MOCK_USER",
                "MOCK_REQUEST_ID");
    }

    @BeforeEach
    public void initMockProducer() {
        mockProducer = new MockProducer(true, new LongSerializer(), new StringSerializer());
    }

    @AfterAll
    public static void destroy() {
        if (mockProducer != null)
            mockProducer.close();
    }


    @Test
    public void givenKeyValue_whenSend_thenVerifyHistoryIsAvailable() {
        kafkaProducer = new LogsKafkaProducer(mockProducer);
        String logRequestModelAsString = JsonEncoder.encodeLogRequestModelToString(logRequestModel);
        kafkaProducer.send(TOPIC, 0L, logRequestModelAsString);
        assertTrue(mockProducer.history().size() == 1);
    }

    @Test
    public void givenKeyValue_verifyMessageMaxBytes() {
        kafkaProducer = new LogsKafkaProducer(mockProducer);

        StringBuilder stringB = new StringBuilder(2000);
        String paddingString = "abcdefghijklmnopqrs";

        while (stringB.length() + paddingString.length() < 2000)
            stringB.append(paddingString);

        String hugeMessage = stringB.toString();

        logRequestModel.setMessage(hugeMessage);

        String logRequestModelAsString = JsonEncoder.encodeLogRequestModelToString(logRequestModel);
        Future mockTopic = kafkaProducer.send(TOPIC, 0L, logRequestModelAsString);


    }

    @Test
    public void givenKeyValue_whenSend_sendShouldBeImmediatelyCompleted() {

        kafkaProducer = new LogsKafkaProducer(mockProducer);

        String logRequestModelAsString = JsonEncoder.encodeLogRequestModelToString(logRequestModel);
        Future futureResult = kafkaProducer.send(TOPIC, 0L, logRequestModelAsString);

        assertTrue(futureResult.isDone());

    }

    @Test
    public void givenKeyValue_whenSend_sendShouldBeSuccessful() {

        kafkaProducer = new LogsKafkaProducer(mockProducer);

        String logRequestModelAsString = JsonEncoder.encodeLogRequestModelToString(logRequestModel);
        Future futureResult = kafkaProducer.send(TOPIC, 0L, logRequestModelAsString);

        assertFalse(isError(futureResult));

    }

    @Test
    public void givenKeyValue_whenSend_offsetShouldBeZero() {

        kafkaProducer = new LogsKafkaProducer(mockProducer);

        String logRequestModelAsString = JsonEncoder.encodeLogRequestModelToString(logRequestModel);
        Future<RecordMetadata> futureResult = kafkaProducer.send(TOPIC, 0L, logRequestModelAsString);

        try {
            assertEquals(0, futureResult.get().offset());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenKeyValue_whenSend_topicShouldBeEqualToSentTopic() {

        kafkaProducer = new LogsKafkaProducer(mockProducer);

        String logRequestModelAsString = JsonEncoder.encodeLogRequestModelToString(logRequestModel);
        Future<RecordMetadata> futureResult = kafkaProducer.send(TOPIC, 0L, logRequestModelAsString);

        try {
            assertEquals(TOPIC, futureResult.get().topic());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenKeyValue_whenSend_thenReturnException() {

        kafkaProducer = new LogsKafkaProducer(mockProducer);

        String logRequestModelAsString = JsonEncoder.encodeLogRequestModelToString(logRequestModel);
        Future record = kafkaProducer.send(TOPIC, 0L, logRequestModelAsString);

        RuntimeException e = new RuntimeException();
        mockProducer.errorNext(e);

        try {
            record.get();
        } catch (ExecutionException | InterruptedException ex) {
            assertEquals(e, ex.getCause());
        }

    }

    @Test
    public void givenKeyValue_verifyMessageSerializationType() {

        kafkaProducer = new LogsKafkaProducer(mockProducer);

        assertThrows(ClassCastException.class,
                ()->{kafkaProducer.send(TOPIC, 0L, logRequestModel);} );

    }




}
