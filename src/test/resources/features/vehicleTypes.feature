Feature: Vehicle Types

  Scenario: Motorcycle age validation
    Given a motorcycle exists
    When the driver age is 20
    Then the motorcycle can be rented

  Scenario: Truck special license
    Given a truck exists
    When the driver has a special license
    Then the truck can be rented

  Scenario: Electric vehicle battery
    Given an electric vehicle exists
    When the battery level is 80
    Then the vehicle is ready for rental