package com.demo.service

import com.demo.dto.CreateOrderRequest
import org.springframework.kafka.support.Acknowledgment
import java.util.concurrent.ExecutionException

interface CreateOrderConsumerService {
    @Throws(ExecutionException::class, InterruptedException::class)
    fun createOrderListener(createOrderRequest: CreateOrderRequest, ack: Acknowledgment)
}