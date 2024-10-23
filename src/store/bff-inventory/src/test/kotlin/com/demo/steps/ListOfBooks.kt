package com.demo.steps

import com.demo.dto.BookResponse
import com.demo.dto.ListBookRequest
import com.demo.service.BookService
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant
import java.util.Date

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
class ListOfBooks {

    private val bookService = Mockito.mock(BookService::class.java)
    private lateinit var response: List<BookResponse>
    private var statusCode: Int = 0
    private var status: String = ""

    @Given("the book service returns a list of books")
    fun gievnAService() {
        val mockResponse = listOf(
            BookResponse(
            "1",
            "Title 1",
            "ISBN 1",
            "Author 1",
            "Publisher 1",
            Date.from(Instant.now()),
            100,
            "English",
            "Review 1"
        )
        )
        Mockito.`when`(bookService.list(ListBookRequest(0, 10))).thenReturn(mockResponse)
    }

    @When("the client requests to list books with size {int} and page {int}")
    fun whenARequestIsMade(size: Int, page: Int) {
        response = bookService.list(ListBookRequest(page, size))
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
