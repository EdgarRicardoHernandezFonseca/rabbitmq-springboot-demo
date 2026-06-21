package com.edgar.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPublisherConfig {

    private static final Logger log =
            LoggerFactory.getLogger(
                    RabbitMQPublisherConfig.class);

    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback() {

        return (
                CorrelationData correlationData,
                boolean ack,
                String cause) -> {

            if (ack) {

                log.info(
                        "Message confirmed by RabbitMQ"
                );

            } else {

                log.error(
                        "Message NOT confirmed. Cause: {}",
                        cause
                );
            }
        };
    }
}