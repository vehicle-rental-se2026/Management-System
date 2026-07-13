Feature: View Available Vehicles

  Scenario: Display available vehicles
    Given the vehicle list exists
    When the manager views available vehicles
    Then only available vehicles should be displayed