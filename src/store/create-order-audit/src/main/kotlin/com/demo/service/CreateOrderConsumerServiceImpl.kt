package com.demo.service

import com.demo.dto.CreateOrderRequest
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

@Service("NotificationService")
class CreateOrderConsumerServiceImpl @Autowired constructor(
    private val documentDBServiceImpl: DocumentDBServiceImpl
) : CreateOrderConsumerService {

    @KafkaListener(
        topics = ["\${kafka.order.topic.create-order}"],
        containerFactory = "NotificationContainerFactory"
    )
    override fun createOrderListener(@Payload createOrderRequest: CreateOrderRequest, ack: Acknowledgment) {
        log.info("Notification service received CreateOrderRequest {} ", createOrderRequest)
        ack.acknowledge()

        documentDBServiceImpl.insertDocument(UUID.randomUUID().toString(), createOrderRequest)
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateOrderConsumerServiceImpl::class.java)
    }
}
