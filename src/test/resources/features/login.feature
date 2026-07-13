Feature: Manager Login

  Scenario: Valid Login
    Given the manager username is "admin"
    And the manager password is "1234"
    When the manager logs in
    Then the login should be successful

  Scenario: Invalid Login
    Given the manager username is "admin"
    And the manager password is "1111"
    When the manager logs in
    Then the login should fail