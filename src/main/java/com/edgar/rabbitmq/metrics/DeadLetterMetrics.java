package com.edgar.rabbitmq.metrics;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Component
public class DeadLetterMetrics {

    private final Counter deadLetters;

    public DeadLetterMetrics(
            MeterRegistry registry) {

        this.deadLetters =
                Counter.builder(
                        "orders_deadletter_total")
                       .description(
                        "Orders sent to DLQ")
                       .register(registry);
    }

    public void increment() {
        deadLetters.increment();
    }
}