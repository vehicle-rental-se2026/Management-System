Feature: Rental Cost

  Scenario: Calculate rental cost

    Given a rental for 5 days
    When the rental cost is calculated
    Then the total cost should be 232.0