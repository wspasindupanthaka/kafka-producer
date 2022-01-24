package com.pasindu.kafkaproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;
import com.pasindu.kafkaproducer.service.ProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/log")
@RestController
public class LogController {

    private ProducerService logService;

    public LogController(ProducerService logService) {
        this.logService = logService;
    }

    @PostMapping
    void writeLog(@RequestBody LogRequestModel logRequestModel) {
        System.out.println(Thread.currentThread().getName()+" Start");
        try {
            logService.createLog(logRequestModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" End");
    }

}
