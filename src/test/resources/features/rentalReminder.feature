Feature: Rental Reminder

  Scenario: Send rental reminder

    Given a rented vehicle
    When the rental reminder is generated
    Then a reminder should be sent