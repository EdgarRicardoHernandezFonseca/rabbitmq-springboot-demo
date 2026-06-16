package com.edgar.rabbitmq.consumer;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.event.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AuditConsumer {

    @RabbitListener(
            queues = RabbitMQConfig.AUDIT_QUEUE
    )
    public void auditOrder(
            OrderCreatedEvent event) {

        System.out.println(
                "AUDIT SERVICE -> Auditing order "
                        + event.getOrderId()
                        + " amount "
                        + event.getTotalAmount()
        );
    }
}