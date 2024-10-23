package com.demo.service

import com.demo.dto.CreateOrderRequest
import com.demo.dto.WebResponse
import java.util.concurrent.ExecutionException

interface CreateOrderProducerService {
    @Throws(ExecutionException::class, InterruptedException::class)
    fun sendCreateOrderEvent(createOrderRequest: CreateOrderRequest?): WebResponse
}
