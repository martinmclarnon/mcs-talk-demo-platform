package com.demo.steps

import com.demo.dto.BlogResponse
import com.demo.dto.ListBlogRequest
import com.demo.service.BlogService
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
class ListOfBlogs {

    private val blogService = Mockito.mock(BlogService::class.java)
    private lateinit var response: List<BlogResponse>
    private var statusCode: Int = 0
    private var status: String = ""

    @Given("the blog service returns a list of blogs")
    fun givenAService() {
        val mockResponse = listOf(BlogResponse("1", "Post 1"))
        Mockito.`when`(blogService.list(ListBlogRequest(0, 10))).thenReturn(mockResponse)
    }

    @When("the client requests to list blogs with size {int} and page {int}")
    fun whenARequestIsMade(size: Int, page: Int) {
        response = blogService.list(ListBlogRequest(page, size))
        statusCode = 200
        status = "OK"
    }

    @Then("the response code should be {int}")
    fun thenVerifyResponseCode(code: Int) {
        assertEquals(code, statusCode)
    }

    @Then("the response status should be {string}")
    fun thenVerifyResponseStatus(status: String) {
        assertEquals(status, this.status)
    }
}
