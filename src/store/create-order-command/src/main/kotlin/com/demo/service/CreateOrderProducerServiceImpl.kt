package com.demo.service

import com.demo.dto.CreateOrderRequest
import com.demo.dto.WebResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException

@Service
class CreateOrderProducerServiceImpl(
    private val createOrderKafkaTemplate: KafkaTemplate<String, CreateOrderRequest>,
    @Value("\${kafka.order.topic.create-order}") private val createOrderTopic: String
) : CreateOrderProducerService {

    @Throws(ExecutionException::class, InterruptedException::class)
    override fun sendCreateOrderEvent(createOrderRequest: CreateOrderRequest?): WebResponse {
        val sendResult: SendResult<String, CreateOrderRequest?> = createOrderKafkaTemplate.send(createOrderTopic, createOrderRequest).get()
        log.info("Create order {} event sent via Kafka", createOrderRequest)
        log.info(sendResult.toString())
        return WebResponse(
            orderId = "O-" + System.currentTimeMillis(),
            status = "SUCCESS"
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(CreateOrderProducerServiceImpl::class.java)
    }
}
