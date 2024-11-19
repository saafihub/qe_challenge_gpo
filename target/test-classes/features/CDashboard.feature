Feature: Upload a CSV file to populate the database from UI

  Background:
    Given user logged in to application as clerk
      |username|password|
      |clerk|clerk|

  @UI
  Scenario: Validate User lands on Clerk Dashboard page
    Given user is on Clerk dashboard page
    Then user lands on the header of the clerk dashboard page
    And clerk dashboard page header should be "Clerk Dashboard"

  @UI
  Scenario: Add Hero Button is visible
    Given user is on Clerk dashboard page
    Then verify Add Hero button should be displayed

  @UI
  Scenario: Upload a working record CSV File
    Given user is on Clerk dashboard page
    When user select upload a csv file from Add Hero dropdown
    And user upload working record csv file
    And user click create button
    Then "Created Successfully!" message should be displayed
    And verify the count of "WORKING_CLASS_HEROES" in database table

  @UI
  Scenario: Upload a erroneous record CSV File
    Given user is on Clerk dashboard page
    When user select upload a csv file from Add Hero dropdown
    And user upload erroneous record csv file
    And user click create button
    Then "Unable to create hero!" message should be displayed
    And verify the count of "WORKING_CLASS_HEROES" in database table

  @UI
  Scenario: Upload a CSV File with Column headers
    Given user is on Clerk dashboard page
    When user select upload a csv file from Add Hero dropdown
    And user upload header record csv file
    And user click create button
    Then "Unable to create hero!" message should be displayed
    And verify the count of "WORKING_CLASS_HEROES" in database table

  @UI
  Scenario: Upload a text file no CSV File
    Given user is on Clerk dashboard page
    When user select upload a csv file from Add Hero dropdown
    And user upload text file other than csv file
    And user click create button
    Then "Unable to create hero!" message should be displayed
    And verify the count of "WORKING_CLASS_HEROES" in database table

  @UI
  Scenario: Upload a empty record CSV File
    Given user is on Clerk dashboard page
    When user select upload a csv file from Add Hero dropdown
    And user upload empty record csv file
    And user click create button
    Then "Created Successfully!" message should be displayed
    And verify the count of "WORKING_CLASS_HEROES" in database table

  @UIX
  Scenario: Add a working class hero using Add a hero page
    Given user is on Clerk dashboard page
    When verify Add Hero button should be displayed
    And user select Add from Add Hero dropdown
    Then user enter the working class hero details to add hero
    | natid			| name           | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
    | natid-100010	| Maria Serapovo | FEMALE	|	2002-07-21	|				|	10500	|	750		|	6				|
    And user click create button
    Then "Created Successfully!" message should be displayed




