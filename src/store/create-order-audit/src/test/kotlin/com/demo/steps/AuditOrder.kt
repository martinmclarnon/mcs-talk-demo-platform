package com.demo.steps

import com.demo.dto.CreateOrderRequest
import com.demo.service.CreateOrderConsumerService
import com.demo.service.DocumentDBService
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@SpringBootTest
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
@Component
class AuditOrder {

    private val createOrderConsumerService: CreateOrderConsumerService = mock()
    private val documentDBService: DocumentDBService = mock()
    private val ack: Acknowledgment = mock()

    private lateinit var createOrderRequest: CreateOrderRequest

    @Given("a valid order message with bookId {string}, userId {string}, quantity {string}, and orderDate {string}")
    fun givenAValidOrderMessage(bookId: String, userId: String, quantity: String, orderDate: String) {
        createOrderRequest = CreateOrderRequest(bookId, userId, quantity, orderDate)
    }

    @When("the message is consumed")
    fun whenTheMessageIsConsumed() {
        whenever(createOrderConsumerService.createOrderListener(createOrderRequest, ack))
            .thenAnswer {
                ack.acknowledge()
                documentDBService.insertDocument(createOrderRequest.bookId, createOrderRequest)
            }
        createOrderConsumerService.createOrderListener(createOrderRequest, ack)
    }

    @Then("the document is saved in the audit repository with bookId {string}, userId {string}, quantity {string}, and orderDate {string}")
    fun thenTheDocumentIsSaved(bookId: String, userId: String, quantity: String, orderDate: String) {
        verify(documentDBService).insertDocument(bookId, createOrderRequest)
    }
}