package com.edgar.rabbitmq.producer;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.event.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class MessageProducer {

	private static final Logger log =
	        LoggerFactory.getLogger(
	                MessageProducer.class);
	
    private final RabbitTemplate rabbitTemplate;
    
    public void createOrder(OrderCreatedEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.FANOUT_EXCHANGE,
                "",
                event
        );

        log.info(
                "OrderCreatedEvent sent: {}",
                event.getOrderId()
        );
    }
}