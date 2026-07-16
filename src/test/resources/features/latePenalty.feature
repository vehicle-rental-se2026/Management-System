Feature: Late Return Penalty

  Scenario: Calculate late return penalty

    Given a rental is returned 2 days late
    When the late penalty is calculated
    Then the penalty should be 30.0