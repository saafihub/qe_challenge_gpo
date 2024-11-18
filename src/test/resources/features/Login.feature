Feature: Login Page of a Working Class Hero System

  @UI
  Scenario: Login page title
    Given user is on login page
    When user gets the header of the login page
    Then header of the login page should be "Working Class Hero System"

  @UI
  Scenario: Login with correct credentials
    Given user is on login page
    When user enters username "clerk"
    And user enters password "clerk"
    And user click on Submit button
	Then user lands on the header of the clerk dashboard page
	And clerk dashboard page header should be "Clerk Dashboard"

  @UI
  Scenario: Login with invalid credentials
    Given user is on login page
    When user enters username "csk01"
    And user enters password "csk01"
    And user click on Submit button
    Then user lands on the error message in the page
    And error message should be "Unable to log in successfully!"

