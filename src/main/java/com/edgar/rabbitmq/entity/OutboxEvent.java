package com.edgar.rabbitmq.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OutboxEvent {

    @Id
    @GeneratedValue
    private Long id;

    private String eventType;

    @Lob
    private String payload;

    private String status;
}