package com.edgar.rabbitmq.service;

import com.edgar.rabbitmq.dto.CreateOrderRequest;
import com.edgar.rabbitmq.event.OrderCreatedEvent;
import com.edgar.rabbitmq.producer.MessageProducer;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageProducer producer;

    public void createOrder(CreateOrderRequest request) { 
    
	    OrderCreatedEvent event =
	            new OrderCreatedEvent(
	                    request.getOrderId(),
	                    request.getCustomerName(),
	                    request.getTotalAmount(),
	                    LocalDateTime.now()
	            );
	    
	    producer.createOrder(event);
    }
}