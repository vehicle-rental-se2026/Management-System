Feature: Rent Vehicle

  Scenario: Rent available vehicle

    Given a vehicle is available
    When the manager rents the vehicle for 5 days
    Then the rental should be successful
    And the vehicle should become unavailable