Feature: Return Vehicle

  Scenario: Return rented vehicle

    Given a rented vehicle exists
    When the manager returns the vehicle
    Then the vehicle becomes available
    And the rental is closed