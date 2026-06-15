package com.edgar.rabbitmq.service;

import com.edgar.rabbitmq.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageProducer producer;

    public void sendMessage(String message) {
        producer.sendMessage(message);
    }
}