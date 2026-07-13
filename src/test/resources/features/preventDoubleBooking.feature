Feature: Prevent Double Booking

  Scenario: Rent already rented vehicle

    Given a vehicle is already rented
    When the manager tries to rent the same vehicle
    Then the rental should be rejected