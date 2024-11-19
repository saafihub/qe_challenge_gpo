Feature: Adding Vouchers to working class heroes using API

  @API
  Scenario: Creation of Single Working Class Hero with Vouchers
    Given Headers added to Vouchers Request
      |HEADER         |VALUE             |
      |Content-Type   | application/json |
    And Make request for a Single working class hero and vouchers with below details
      | natid			| name            | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-100002	| Stephen Richard | MALE	|	1998-10-25	|				|	10000	|	950		|	7				|
    When send vouchers request to "api/v1/hero/vouchers"
    Then verify vouchers response statuscode 200

  @API
  Scenario: Check created vouchers added in database
    Given user execute vouchers query "SELECT * FROM vouchers WHERE name = 'VOUCHER 1'"
    Then user should receive a non empty resultset added with vouchers

  @API
  Scenario: Single Working Class Hero creation with empty Vouchers
    Given Headers added to Vouchers Request
      |HEADER         |VALUE|
      |Content-Type   | application/json |
    And Make request for a Single working class hero and empty vouchers with below details
      | natid			| name           | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-100002	| Stephen Richard| MALE	    |	1998-10-25	|				|	10000	|	950		|	7				|
    When send vouchers request to "api/v1/hero/vouchers"
    Then verify vouchers response statuscode 400
    And  verify response message as "vouchers cannot be null or empty"

  @API
  Scenario: Check vouchers added in db, which created with invalid details.
    Given Headers added to Vouchers Request
      |HEADER         |VALUE             |
      |Content-Type   | application/json |
    And Make request for a Single working class hero and vouchers with below invalid details
      | natid			| name            | gender	|	birthDate	|	deathDate	|	salary	|	taxPaid	|	browniePoints	|
      | natid-100001	| Maria Serapovo    | FEMALE	|	1998-10-25	|				|	11250	|	950		|	7				|
    Then user execute vouchers query "SELECT * FROM vouchers WHERE name = 'VOUCHER 2'"
    And user should receive a non empty resultset added with vouchers

  @API
  Scenario: Number of vouchers each customer has each voucher category
    Given Headers added to Vouchers Request
      |HEADER         |VALUE             |
      |Content-Type   | application/json |
    When send vouchers request by person and type to "api/v1/voucher/by-person-and-type"
    Then verify vouchers response statuscode 200
    And  verify response body for vouchers