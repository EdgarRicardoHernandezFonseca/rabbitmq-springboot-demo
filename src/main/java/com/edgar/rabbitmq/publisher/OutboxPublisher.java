package com.edgar.rabbitmq.publisher;

import java.util.List;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.entity.OutboxEvent;
import com.edgar.rabbitmq.event.OrderCreatedEvent;
import com.edgar.rabbitmq.repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OutboxEventRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    
    private static final Logger log =
	        LoggerFactory.getLogger(
	        		OutboxPublisher.class);

    @Scheduled(fixedDelay = 5000)
    public void publishPendingEvents()
            throws Exception {
    	
        List<OutboxEvent> events =
                repository.findByStatus(
                        "PENDING");
        
        if (events.isEmpty()) {

            log.debug(
                "No pending events found"
            );

            return;
        }

        for (OutboxEvent event : events) {

            OrderCreatedEvent orderEvent =
                    objectMapper.readValue(
                            event.getPayload(),
                            OrderCreatedEvent.class);

            CorrelationData correlationData =
                    new CorrelationData(
                            String.valueOf(
                                    event.getId()));

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.FANOUT_EXCHANGE,
                    "",
                    orderEvent,
                    correlationData);

            boolean confirmed =
                    correlationData
                            .getFuture()
                            .get()
                            .isAck();

            if (confirmed) {

                event.setStatus("SENT");

                repository.save(event);

                log.info(
                        "Outbox event {} published",
                        event.getId());

            } else {

                log.error(
                        "RabbitMQ rejected message {}",
                        event.getId());
            }
        }
    }
}