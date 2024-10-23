package com.demo.controller


import com.demo.dto.CreateOrderRequest
import com.demo.service.CreateOrderProducerService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ExecutionException

@RequestMapping("/v1/x1wwg6t2hdc5ukvejdf5")
@RestController
class OrderController(
    private val createOrderProducerService: CreateOrderProducerService,  // Using the interface
    @Value("\${kafka.order.topic.create-order}") private val createOrderTopic: String,
    @Value("\${kafka.order.bootstrap-servers}") private val servers: String
) {

    @PostMapping
    @Throws(ExecutionException::class, InterruptedException::class)
    fun createOrder(@RequestBody createOrderRequest: CreateOrderRequest): ResponseEntity<*> {
        log.info("{}", createOrderRequest)
        createOrderProducerService.sendCreateOrderEvent(createOrderRequest)  // Use the interface
        return ResponseEntity<Any>(HttpStatus.OK)
    }

    @GetMapping("/env")
    fun getEnvInfo(): String {
        return "$createOrderTopic - $servers"
    }

    companion object {
        private val log = LoggerFactory.getLogger(OrderController::class.java)
    }
}
