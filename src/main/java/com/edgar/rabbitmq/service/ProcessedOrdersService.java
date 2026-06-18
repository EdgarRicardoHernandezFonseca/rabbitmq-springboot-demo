package com.edgar.rabbitmq.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class ProcessedOrdersService {

    private final Set<Long> processedOrders =
            ConcurrentHashMap.newKeySet();

    public boolean isProcessed(Long orderId) {

        return processedOrders.contains(orderId);
    }

    public void markProcessed(Long orderId) {

        processedOrders.add(orderId);
    }
}