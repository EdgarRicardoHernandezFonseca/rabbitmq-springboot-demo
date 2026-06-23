package com.edgar.rabbitmq.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.stereotype.Component;

@Component
public class OrderMetrics {

    private final Counter processedOrders;
    private final Counter failedOrders;

    public OrderMetrics(MeterRegistry registry) {

        this.processedOrders =
                Counter.builder(
                        "orders_processed_total")
                        .description(
                                "Total processed orders")
                        .register(registry);

        this.failedOrders =
                Counter.builder(
                        "orders_failed_total")
                        .description(
                                "Total failed orders")
                        .register(registry);
    }

    public void incrementProcessed() {
        processedOrders.increment();
    }

    public void incrementFailed() {
        failedOrders.increment();
    }
}