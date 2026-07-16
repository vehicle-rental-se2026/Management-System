Feature: Manager Logout

  Scenario: Successful Logout
    Given the manager is logged in
    When the manager logs out
    Then the manager should be logged out