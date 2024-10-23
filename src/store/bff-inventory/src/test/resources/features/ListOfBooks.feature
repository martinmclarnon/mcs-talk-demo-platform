# ListofBooks.feature

Feature: List Books
  As a user, I want to list books so that I can see the content available.

  Scenario: Successfully list books
    Given the book service returns a list of books
    When the client requests to list books with size 10 and page 0
    Then the response code should be 200
    And the response status should be "OK"