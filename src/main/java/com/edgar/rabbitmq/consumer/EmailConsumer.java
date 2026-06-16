package com.edgar.rabbitmq.consumer;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.event.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EmailConsumer {
	
	private static final Logger log =
	        LoggerFactory.getLogger(
	                EmailConsumer.class);

    @RabbitListener(
            queues = RabbitMQConfig.EMAIL_QUEUE
    )
    public void receiveOrder(
            OrderCreatedEvent event) {
    	
//    	throw new RuntimeException(
//                "SMTP Server unavailable"
//        );

    	log.info(
    	        "EMAIL SERVICE -> Sending email for order {}",
    	        event.getOrderId()
    	);
    }
}