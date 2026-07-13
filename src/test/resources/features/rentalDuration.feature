Feature: Rental Duration

  Scenario: Invalid rental duration

    Given a vehicle is available
    When the manager rents the vehicle for -1 days
    Then the rental should fail