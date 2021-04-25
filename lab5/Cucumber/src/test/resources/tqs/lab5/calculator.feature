Feature: Basic Arithmetic
  Background: A Calculator
    Given A calculator I just turned on
  Scenario: Addition
    When I add 19 and 2
    Then the result is 21
  Scenario: Subtraction
    When I subtract 40 to 300
    Then the result is -260
  Scenario Outline: Several additions
    When I add <a> and <b>
    Then the result is <c>
    Examples: Single digits
      | a | b | c  |
      | 1 | 2 | 3  |
      | 3 | 7 | 10 |
  Scenario Outline: Several subtractions
    When I subtract <a> to <b>
    Then the result is <c>
    Examples: Single digits
      | a | b | c  |
      | 3 | 2 | 1  |
      | 3 | 7 | -4 |