package com.edgar.rabbitmq.service;

import org.springframework.stereotype.Service;

import com.edgar.rabbitmq.entity.OutboxEvent;
import com.edgar.rabbitmq.event.OrderCreatedEvent;
import com.edgar.rabbitmq.repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ObjectMapper objectMapper;
    private final OutboxEventRepository repository;

    public void createOrder(
            OrderCreatedEvent event)
            throws Exception {

        OutboxEvent outbox =
                new OutboxEvent();

        outbox.setEventType(
                "ORDER_CREATED");

        outbox.setPayload(
                objectMapper.writeValueAsString(
                        event));

        outbox.setStatus(
                "PENDING");

        repository.save(outbox);
    }
}
