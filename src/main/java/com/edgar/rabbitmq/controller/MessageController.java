package com.edgar.rabbitmq.controller;

import com.edgar.rabbitmq.dto.MessageRequest;
import com.edgar.rabbitmq.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<String> sendMessage(
            @RequestBody MessageRequest request) {

        messageService.sendMessage(
                request.getMessage()
        );

        return ResponseEntity.ok(
                "Message sent successfully"
        );
    }
}