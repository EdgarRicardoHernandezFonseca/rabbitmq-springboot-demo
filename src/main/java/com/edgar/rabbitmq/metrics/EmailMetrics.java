package com.edgar.rabbitmq.metrics;

import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

@Component
public class EmailMetrics {

    private final Counter processedOrders;
    private final Counter failedOrders;
    private final Timer processingTimer;

    public EmailMetrics(MeterRegistry registry) {

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

        this.processingTimer =
                Timer.builder(
                        "order_processing_time")
                     .description(
                        "Order processing duration")
                     .register(registry);
    }

    public void incrementProcessed() {
        processedOrders.increment();
    }

    public void incrementFailed() {
        failedOrders.increment();
    }

    public Timer getProcessingTimer() {
        return processingTimer;
    }
}