package com.edgar.rabbitmq.service;

import org.springframework.stereotype.Service;

import com.edgar.rabbitmq.event.OrderCreatedEvent;

@Service
public class EmailService {

    public void sendEmail(
            OrderCreatedEvent event) {

        System.out.println(
                "Email sent to "
                        + event.getCustomerName());
    }
}