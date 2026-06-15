package com.edgar.rabbitmq.controller;

import com.edgar.rabbitmq.dto.CreateOrderRequest;
import com.edgar.rabbitmq.dto.MessageRequest;
import com.edgar.rabbitmq.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<String> createOrder(
            @RequestBody CreateOrderRequest request) {

        messageService.createOrder(request);

        return ResponseEntity.ok(
                "Order event sent successfully"
        );
    }
}