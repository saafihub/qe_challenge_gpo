Feature: External Service Api calls
  @API
  Scenario: Check owe-money api with valid natid
    Given Headers added to ExternalService Request
      |HEADER         |VALUE|
      |Content-Type| application/json |
    When send GET request to ExternalService "api/v1/hero/owe-money?natid=8525"
    Then ExternalService response status code is 200
    And  Response body contains "natid-8525" and "OWE"

  @API
  Scenario: Check owe-money api with invalid natid
    Given Headers added to ExternalService Request
      |HEADER         |VALUE|
      |Content-Type| application/json |
    When send GET request to ExternalService "api/v1/hero/owe-money?natid=abcd"
    Then ExternalService response status code is 500
    And  Response body contains "Internal Server Error"

  @API
  Scenario: Check owe-money api with valid natid and validate its response format
    Given Headers added to ExternalService Request
      |HEADER         |VALUE|
      |Content-Type| application/json |
    When send GET request to ExternalService "api/v1/hero/owe-money?natid=8525"
    Then ExternalService response status code is 200
    And  verify response body format "natid-8525" and "OWE"

  @API
  Scenario: Check owe-money api with valid natid and validating its response schema structure
    Given Headers added to ExternalService Request
      |HEADER         |VALUE|
      |Content-Type| application/json |
    When send GET request to ExternalService "api/v1/hero/owe-money?natid=8525"
    Then ExternalService response status code is 200
    And  verify response body schema structure "natid-8525" and "OWE"