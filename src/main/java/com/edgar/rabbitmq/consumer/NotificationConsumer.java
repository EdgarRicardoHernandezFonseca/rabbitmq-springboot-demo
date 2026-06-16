package com.edgar.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.event.OrderCreatedEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class NotificationConsumer {
	
	private static final Logger log =
	        LoggerFactory.getLogger(
	        		NotificationConsumer.class);
	
	@RabbitListener(
	        queues = RabbitMQConfig.DLQ
	)
	public void notifyFailure(
	        OrderCreatedEvent event) {

	    log.error(
	            "ALERT -> Order {} failed permanently",
	            event.getOrderId()
	    );
	}

}
