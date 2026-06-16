package com.edgar.rabbitmq.consumer;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.event.OrderCreatedEvent;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EmailConsumer {
	
	private static final Logger log =
	        LoggerFactory.getLogger(
	                EmailConsumer.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RabbitListener(
	        queues = RabbitMQConfig.EMAIL_QUEUE
	)
	public void receiveOrder(
	        OrderCreatedEvent event) {

	    try {

	        throw new RuntimeException(
	                "SMTP unavailable"
	        );

	    } catch (Exception ex) {

	        log.warn(
	                "Email failed. Sending to retry queue"
	        );

	        rabbitTemplate.convertAndSend(
	                "",
	                RabbitMQConfig.RETRY_QUEUE,
	                event
	        );
	    }
	}
}