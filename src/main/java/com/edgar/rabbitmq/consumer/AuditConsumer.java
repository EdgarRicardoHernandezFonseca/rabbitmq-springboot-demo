package com.edgar.rabbitmq.consumer;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.event.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AuditConsumer {
	
	private static final Logger log =
	        LoggerFactory.getLogger(
	                AuditConsumer.class);

    @RabbitListener(
            queues = RabbitMQConfig.AUDIT_QUEUE
    )
    public void auditOrder(
            OrderCreatedEvent event) {

    	log.info(
    	        "AUDIT SERVICE -> Order {} Amount {}",
    	        event.getOrderId(),
    	        event.getTotalAmount()
    	);
    }
}