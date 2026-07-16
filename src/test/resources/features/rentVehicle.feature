Feature: Rent Vehicle

  Scenario: Rent an available vehicle
    Given an available vehicle exists
    When the manager rents the vehicle for 5 days
    Then the rental should be successful
    And the vehicle should become unavailable