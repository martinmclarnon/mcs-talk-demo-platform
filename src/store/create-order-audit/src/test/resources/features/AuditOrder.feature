Feature: Save order payload in audit log

  Scenario: Successfully consume order message and save in audit log
    Given a valid order message with bookId "123", userId "456", quantity "2", and orderDate "2024-10-22"
    When the message is consumed
    And the document is saved in the audit repository with bookId "123", userId "456", quantity "2", and orderDate "2024-10-22"