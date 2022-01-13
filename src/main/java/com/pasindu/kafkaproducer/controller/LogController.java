package com.pasindu.kafkaproducer.controller;

import com.pasindu.kafkaproducer.kafka.model.LogRequestModel;
import com.pasindu.kafkaproducer.service.LogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/log")
@RestController
public class LogController {

    private LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    void writeLog(@RequestBody LogRequestModel logRequestModel) {
        logService.createLog(logRequestModel);
    }
}
