package com.edgar.rabbitmq.metrics;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class OutboxMetrics {

    private final Counter publishedEvents;

    public OutboxMetrics(MeterRegistry registry) {

        this.publishedEvents =
                Counter.builder(
                        "outbox_events_published_total")
                       .description(
                        "Published outbox events")
                       .register(registry);
    }

    public void incrementPublished() {
        publishedEvents.increment();
    }
}