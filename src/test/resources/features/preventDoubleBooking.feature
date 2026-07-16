Feature: Prevent Double Booking

  Scenario: Prevent renting the same vehicle twice
    Given an available vehicle exists
    When the manager rents the vehicle for 5 days
    And the manager rents the vehicle for 3 days
    Then the rental should be rejected