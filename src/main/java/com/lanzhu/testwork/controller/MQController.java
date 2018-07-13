package com.lanzhu.testwork.controller;

import com.lanzhu.testwork.rabbitmq.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MQController {

    @Autowired
    private Producer producer;

    @RequestMapping(value = "/test/mq/ttl", method = RequestMethod.GET)
    public ResponseEntity<String> testMQTtl() {
        log.info("get and request to test mq ttl.");
        producer.createMQMessage();
        return ResponseEntity.ok().body("Create MQ message success.");
    }
}
