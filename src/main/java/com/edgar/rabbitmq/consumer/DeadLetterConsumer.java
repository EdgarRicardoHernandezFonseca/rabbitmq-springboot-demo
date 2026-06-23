package com.edgar.rabbitmq.consumer;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.event.OrderCreatedEvent;
import com.edgar.rabbitmq.metrics.DeadLetterMetrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(
                    DeadLetterConsumer.class);
    
    public final DeadLetterMetrics deadLetterMetrics;
    
    public DeadLetterConsumer(DeadLetterMetrics deadLetterMetrics) {
    	this.deadLetterMetrics = deadLetterMetrics;
    }

    @RabbitListener(
            queues = RabbitMQConfig.DLQ
    )
    public void consumeFailedMessage(
            OrderCreatedEvent event, 
            DeadLetterMetrics deadLetterMetrics) {
    	
    	deadLetterMetrics.increment();

        log.error(
                "DLQ -> Failed Order {} Customer {}",
                event.getOrderId(),
                event.getCustomerName()
        );
    }
}