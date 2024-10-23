// CreateOrderRequest.kt

package com.demo.dto

data class CreateOrderRequest(
    val bookId: String,
    val userId: String,
    val quantity: String,
    val orderDate: String
)
