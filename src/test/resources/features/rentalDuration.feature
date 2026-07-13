Feature: Rental Duration

  Scenario: Invalid rental duration
    Given an available vehicle exists
    When the manager rents the vehicle for -1 days
    Then the rental should be rejected