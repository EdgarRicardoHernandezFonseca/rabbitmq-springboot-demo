package com.edgar.rabbitmq.consumer;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.event.OrderCreatedEvent;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;

@Component
public class EmailConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(
                    EmailConsumer.class);

    private static final int MAX_RETRIES = 3;

    private final RabbitTemplate rabbitTemplate;

    public EmailConsumer(
            RabbitTemplate rabbitTemplate) {

        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(
            queues = RabbitMQConfig.EMAIL_QUEUE
    )
    public void receiveOrder(
            OrderCreatedEvent event,
            Message message) {

    	int retryCount = 0;

    	Object xDeath =
    	        message.getMessageProperties()
    	                .getHeaders()
    	                .get("x-death");

    	if (xDeath instanceof java.util.List<?> deaths
    	        && !deaths.isEmpty()) {

    	    Object first =
    	            deaths.get(0);

    	    if (first instanceof java.util.Map<?, ?> deathInfo) {

    	        Object count =
    	                deathInfo.get("count");

    	        if (count instanceof Long) {
    	            retryCount =
    	                    ((Long) count).intValue();
    	        }
    	    }
    	}
    	
        log.info(
                "Headers: {}",
                message.getMessageProperties()
                        .getHeaders()
        );
        
        if (retryCount == 0) {

            rabbitTemplate.send(
                    "",
                    RabbitMQConfig.RETRY_5S_QUEUE,
                    message
            );

            return;
        }
        
        if (retryCount == 1) {

            rabbitTemplate.send(
                    "",
                    RabbitMQConfig.RETRY_15S_QUEUE,
                    message
            );

            return;
        }
        
        if (retryCount == 2) {

            rabbitTemplate.send(
                    "",
                    RabbitMQConfig.RETRY_30S_QUEUE,
                    message
            );

            return;
        }
        
        if (retryCount >= MAX_RETRIES) {

            log.error(
                    "Max retries reached for order {}",
                    event.getOrderId()
            );

            throw new AmqpRejectAndDontRequeueException(
                    "Max retries exceeded"
            );
        }

        log.warn(
                "Email failed. Retry {} of {} for order {}",
                retryCount + 1,
                MAX_RETRIES,
                event.getOrderId()
        );
    }
}