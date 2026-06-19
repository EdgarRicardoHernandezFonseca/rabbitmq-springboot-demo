package com.edgar.rabbitmq.publisher;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.entity.OutboxEvent;
import com.edgar.rabbitmq.repository.OutboxEventRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OutboxEventRepository repository;
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 5000)
    public void publishEvents() {

        List<OutboxEvent> events =
                repository.findByStatus("PENDING");

        for (OutboxEvent event : events) {

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.FANOUT_EXCHANGE,
                    "",
                    event.getPayload()
            );

            event.setStatus("SENT");

            repository.save(event);
        }
    }
}