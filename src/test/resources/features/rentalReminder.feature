Feature: Rental Reminder

  Scenario: Send rental reminder
    Given a rented vehicle is close to expiry
    When the reminder is sent
    Then the notification should be generated