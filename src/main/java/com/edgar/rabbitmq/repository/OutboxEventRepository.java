package com.edgar.rabbitmq.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edgar.rabbitmq.entity.OutboxEvent;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent,Long> {

	List<OutboxEvent>
	findByStatus(String status);
}