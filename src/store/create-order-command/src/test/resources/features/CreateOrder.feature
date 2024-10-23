Feature: Create Order
  As a user, I want to place an order so I can read my book

  Scenario: Creating a new order
    Given a valid order payload
    When the client sends a POST request to create the order
    Then the response code should be 200
    And the response status should be "OK"
