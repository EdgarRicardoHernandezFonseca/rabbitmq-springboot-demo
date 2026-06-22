package com.edgar.rabbitmq.consumer;

import com.edgar.rabbitmq.config.RabbitMQConfig;
import com.edgar.rabbitmq.event.OrderCreatedEvent;
import com.edgar.rabbitmq.service.EmailService;
import com.edgar.rabbitmq.service.ProcessedOrdersService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;

import com.rabbitmq.client.Channel;

@Component
public class EmailConsumer {

    private static final Logger log =
            LoggerFactory.getLogger(
                    EmailConsumer.class);

    private static final int MAX_RETRIES = 3;

    private final RabbitTemplate rabbitTemplate;
    
    private final ProcessedOrdersService processedOrdersService;
    
    private final EmailService emailService;

    public EmailConsumer(
            RabbitTemplate rabbitTemplate,
            ProcessedOrdersService processedOrdersService,
            EmailService emailService) {

        this.rabbitTemplate = rabbitTemplate;
        this.processedOrdersService = processedOrdersService;
        this.emailService = emailService;
    }
    @RabbitListener(
            queues = RabbitMQConfig.EMAIL_QUEUE
    )
    public void receiveOrder(
            OrderCreatedEvent event,
            Message message,
            Channel channel) {
    	
    	long deliveryTag =
		        message.getMessageProperties()
		               .getDeliveryTag();
    	
    	try {
    		
    		Long orderId =
    	            event.getOrderId();

    		validarDuplicado(orderId);

    	    enviarEmail(event);

    	    marcarProcesado(orderId);
    	    
    	    basicAck(deliveryTag, channel);

    	} catch (Exception e) {

    		procesarRetry(event, message);
            
            basicNack(deliveryTag, channel);
    	}
    }
    
    private int getRetryCount(Message message) {

        int retryCount = 0;

        Object xDeath =
                message.getMessageProperties()
                        .getHeaders()
                        .get("x-death");

        if (xDeath instanceof List<?> deaths) {

            for (Object death : deaths) {

                if (death instanceof Map<?, ?> deathInfo) {

                    Object count =
                            deathInfo.get("count");

                    if (count instanceof Long) {

                        retryCount +=
                                ((Long) count).intValue();
                    }
                }
            }
        }
        
        log.info(
                "Headers: {}",
                message.getMessageProperties()
                        .getHeaders()
        );

        return retryCount;
    }
    
    public void validarDuplicado(Long orderId) {
    	
    	 if (processedOrdersService
 	            .isProcessed(orderId)) {

 	        log.warn(
 	                "Duplicate order {} ignored",
 	                orderId);

 	        return;
 	    }
    	
    }
    
    public void enviarEmail(OrderCreatedEvent event) {
    	
    	emailService.sendEmail(event);
    }
    
    public void marcarProcesado(Long orderId) {
    	
    	processedOrdersService
        .markProcessed(orderId);

		log.info(
		        "Order {} processed successfully",
		        orderId);
    }
    
    public void basicAck(long deliveryTag, Channel channel) {
    	
    	try {
			channel.basicAck(
			        deliveryTag,
			        false);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void procesarRetry(OrderCreatedEvent event, Message message) {
    	
    	int retryCount =
                getRetryCount(message);
        
        if (retryCount == 0) {

            rabbitTemplate.send(
                    "",
                    RabbitMQConfig.RETRY_5S_QUEUE,
                    message
            );

            return;
        }
        
        if (retryCount == 1) {

            rabbitTemplate.send(
                    "",
                    RabbitMQConfig.RETRY_15S_QUEUE,
                    message
            );

            return;
        }
        
        if (retryCount == 2) {

            rabbitTemplate.send(
                    "",
                    RabbitMQConfig.RETRY_30S_QUEUE,
                    message
            );

            return;
        }
        
        if (retryCount >= MAX_RETRIES) {

            log.error(
                    "Max retries reached for order {}",
                    event.getOrderId()
            );

            throw new AmqpRejectAndDontRequeueException(
                    "Max retries exceeded"
            );
        }

        log.warn(
                "Email failed. Retry {} of {} for order {}",
                retryCount + 1,
                MAX_RETRIES,
                event.getOrderId()
        );
    }
    
    public void basicNack(long deliveryTag, Channel channel) {
    	
    	try {
			channel.basicNack(
			        deliveryTag,
			        false,
			        false
			);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
}