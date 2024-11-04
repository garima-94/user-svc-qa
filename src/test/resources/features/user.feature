@user
Feature: To test the user scenarios

  Background: Launch the application
    Given Application is running

  Scenario: Successful GET users request
    Given Users have been created in the system
    When I fetch all the users
    Then a list of users should be returned with name and email
      | name  | email           |
      | steve | steve@zilch.com |
      | ken   | ken@zilch.com   |

  Scenario: Unsuccessful GET address request
    Given An user exist in the system without address
    When I fetch the address of the user
    Then An address should not be returned

  Scenario Outline: Successful POST of a new user
    Given Users have been created in the system
    When a request for "<name>" and "<email>" is made
    Then the message "<response>" should be returned
    Examples:
      | name  | email           | response            |
      | clark | ken@zilch.com   | ken updated         |
      | steve | steve@zilch.com | user already exists |

  Scenario: To test successful deletion of user
    Given An user exists with id as 1
    When I delete the user
    Then I should see one user in the system
      | name  | email         |
      | clark | ken@zilch.com |