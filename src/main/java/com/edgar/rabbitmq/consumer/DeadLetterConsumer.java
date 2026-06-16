package com.edgar.rabbitmq.consumer;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DeadLetterConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(
                    DeadLetterConsumer.class);

    @RabbitListener(
            queues = RabbitMQConfig.DLQ
    )
    public void consumeFailedMessage(
            OrderCreatedEvent event) {

        log.error(
                "DLQ -> Failed Order {} Customer {}",
                event.getOrderId(),
                event.getCustomerName()
        );
    }
}