package com.edgar.rabbitmq.controller;

import com.edgar.rabbitmq.event.OrderCreatedEvent;
import com.edgar.rabbitmq.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class MessageController {

    private final OrderService orderService;
    
    @PostMapping
    public ResponseEntity<String> createOrder(
            @RequestBody OrderCreatedEvent event) throws Exception {

        orderService.createOrder(event);

        return ResponseEntity.ok(
                "Order event sent successfully"
        );
    }
}