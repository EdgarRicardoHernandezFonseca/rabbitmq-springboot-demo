package com.edgar.rabbitmq.consumer;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.event.OrderCreatedEvent;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
  
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveMessage(OrderCreatedEvent event) {

        System.out.println(
                "Order received: "
                + event.getOrderId()
                + " - "
                + event.getCustomerName()
        );
    }
}