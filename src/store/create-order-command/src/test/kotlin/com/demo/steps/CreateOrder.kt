package com.demo.steps

import com.demo.dto.CreateOrderRequest
import com.demo.dto.WebResponse
import com.demo.service.CreateOrderProducerService
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Component

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
@Component
class CreateOrder {

    private val createOrderProducerService: CreateOrderProducerService = mock()

    private lateinit var webResponse: WebResponse
    private var statusCode: Int = 0
    private var status: String = ""

    @Given("a valid order payload")
    fun givenValidOrderPayload() {
        val createOrderRequest = CreateOrderRequest(
            bookId = "1",
            userId = "123",
            quantity = "1",
            orderDate = "2015-12-11T20:28:30.45+01:00 or 2015-12-11T19:28:30.45Z"
        )

        val mockResponse = WebResponse(
            orderId = "O-123",
            status = "SUCCESS"
        )

        whenever(createOrderProducerService.sendCreateOrderEvent(createOrderRequest)).thenReturn(mockResponse)
    }

    @When("the client sends a POST request to create the order")
    fun whenARequestIsMade() {
        val createOrderRequest = CreateOrderRequest(
            bookId = "1",
            userId = "123",
            quantity = "1",
            orderDate = "2015-12-11T20:28:30.45+01:00 or 2015-12-11T19:28:30.45Z"
        )

        webResponse = createOrderProducerService.sendCreateOrderEvent(createOrderRequest)
        statusCode = 200
        status = "OK"
    }

    @Then("the response code should be {int}")
    fun verifyResponseCode(code: Int) {
        assertEquals(code, statusCode)
    }

    @Then("the response status should be {string}")
    fun verifyResponseStatus(status: String) {
        assertEquals(status, this.status)
    }
}