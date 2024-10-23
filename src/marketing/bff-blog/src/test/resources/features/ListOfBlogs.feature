Feature: List Blogs
  As a user, I want to list blogs so that I can see the content available.

  Scenario: Successfully list blogs
    Given the blog service returns a list of blogs
    When the client requests to list blogs with size 10 and page 0
    Then the response code should be 200
    And the response status should be "OK"