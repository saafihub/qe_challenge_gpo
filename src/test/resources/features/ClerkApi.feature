Feature: Working Class Hero creation using API

  @API
  Scenario: Single Working Class Hero Creation
    Given headers added to payload
      |HEADER         |VALUE|
      |Content-Type| application/json |
    And Make request for Single working class hero with below details
      | natid			| name           | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-100001	| Maria Serapovo | FEMALE	|	2002-07-21	|				|	10500	|	750		|	6				|
    When Request Post action Sent To "api/v1/hero"
    Then verify status code is 200

  @API
  Scenario: Check with invalid natid where it contains aplhanumeric characters
    Given headers added to payload
      |HEADER         |VALUE|
      |Content-Type| application/json |
    And Make request for Single working class hero with invalid details
      | natid				| name           | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-abcd100001	| Maria Serapovo | FEMALE	|	2002-07-21	|				|	10500	|	750		|	6				|
    When Request Post action Sent To "api/v1/hero"
    Then verify status code for invalid natid is 400

  @API
  Scenario: Check with invalid natid where it does not prefix with natid
    Given headers added to payload
      |HEADER         |VALUE|
      |Content-Type| application/json |
    And Make request for Single working class hero with invalid details
      | natid			| name           | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | abcd100001	    | Maria Serapovo | FEMALE	|	2002-07-21	|				|	10500	|	750		|	6				|
    When Request Post action Sent To "api/v1/hero"
    Then verify status code for invalid natid is 400

  @API
  Scenario: Check with invalid natid where the number is not inclusive
    Given headers added to payload
      |HEADER         |VALUE|
      |Content-Type| application/json |
    And Make request for Single working class hero with invalid details
      | natid				| name           | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-10000000	    | Maria Serapovo | FEMALE	|	2002-07-21	|				|	10500	|	750		|	6				|
    When Request Post action Sent To "api/v1/hero"
    Then verify status code for invalid natid is 400

  @API
  Scenario: Check with invalid name where the it includes numeric value
    Given headers added to payload
      |HEADER         |VALUE|
      |Content-Type| application/json |
    And Make request for Single working class hero with invalid details
      | natid				| name      | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-12345		| 1User		| FEMALE	|	2002-07-21	|				|	10500	|	750		|	6				|
    When Request Post action Sent To "api/v1/hero"
    Then verify status code for invalid natid is 400
    And  Response message contains "Invalid name"

  @API
  Scenario: Check with invalid name where the it exceeds than allowed limit
    Given headers added to payload
      |HEADER         |VALUE|
      |Content-Type| application/json |
    And Make request for Single working class hero with invalid details
      | natid			| name      | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-12345		| ABCDE1234567890FGH09876543231IJK6789054321LMNOP098765123456QRST12345609876UIWXYZABCDE1234567890FGH09876543231IJK6789054321LMNOP098765123456QRST12345609876UIWXYZ		| MALE	|	2002-11-17	|				|	8500	|	875		|	8				|
    When Request Post action Sent To "api/v1/hero"
    Then verify status code for invalid natid is 400
    And  Response message contains "Name must be between 1 and 100 characters"

  @API
  Scenario: Check with invalid gender
    Given headers added to payload
      |HEADER         |VALUE|
      |Content-Type| application/json |
    And Make request for Single working class hero with invalid details
      | natid				| name      | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-12345		| User		| M			|	2002-07-21	|				|	10500	|	750		|	6				|
    When Request Post action Sent To "api/v1/hero"
    Then verify status code for invalid natid is 400
    And  Response message contains "Invalid gender"

  @API
  Scenario: Check with future deathDate
    Given headers added to payload
      |HEADER         |VALUE|
      |Content-Type| application/json |
    And Make request for Single working class hero with invalid details
      | natid				| name      | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-100005		| User		| MALE		|	2002-07-21	|	2025-12-26	|	10500	|	750		|	6				|
    When Request Post action Sent To "api/v1/hero"
    Then verify status code for invalid natid is 500

  @API
  Scenario: Check with invalid salary
    Given headers added to payload
      |HEADER         |VALUE|
      |Content-Type| application/json |
    And Make request for Single working class hero with invalid details
      | natid				| name      | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-12345		| User		| MALE		|	2002-07-21	|				|	-11000	|	750		|	6				|
    When Request Post action Sent To "api/v1/hero"
    Then verify status code for invalid natid is 400
    And  Response message contains "Salary must be greater than or equals to zero"

  @API
  Scenario: Check with creation of Working Class Hero with nullable browniepoints and deathdate
    Given headers added to payload
      |HEADER         |VALUE|
      |Content-Type| application/json |
    And Make request for Single working class hero with nullable on browniepoints and deathdate
      | natid			| name           | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-100001	| Maria Serapovo | FEMALE	|	2002-07-21	|				|	10500	|	750		|				|
    When Request Post action Sent To "api/v1/hero"
    And verify status code is 200

  @API
  Scenario: Check with invalid taxpaid
    Given headers added to payload
      |HEADER         |VALUE|
      |Content-Type| application/json |
    And Make request for Single working class hero with invalid details
      | natid				| name      | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-12345		| User		| MALE		|	2002-07-21	|				|	10500	|	-750	|	6				|
    When Request Post action Sent To "api/v1/hero"
    Then verify status code for invalid natid is 400
    And  Response message contains "must be greater than or equal to 0"

  @API
  Scenario: Check with creation of duplicate Single Working Class Hero
    Given headers added to payload
      |HEADER         |VALUE|
      |Content-Type| application/json |
    And Make request for Single working class hero with existing natid details
      | natid			| name           | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-100001	| Maria Serapovo | FEMALE	|	2002-07-21	|				|	10500	|	750		|	6				|
      | natid-100001	| Maria Serapovo | FEMALE	|	2002-07-21	|				|	10500	|	750		|	6				|
    When Request Post action Sent To "api/v1/hero"
    Then verify status code is should be either of 400 and 500
    And  Response message contains "Working Class Hero of natid: natid-100001 already exists!"

  @API
  Scenario: Check Created Working Class Hero in database
    Given user establish a database connection
    When user execute query "SELECT * FROM working_class_heroes WHERE natid = natid-100001"
    Then user should receive a non empty resultset
