Feature: To retrieve weather description for a given city and country

  Scenario: Retrieve weather description successfully for a given city and country
    Given The user request for weather description of city and a country
    When the user calls GET "/weather/uk/London?apikey=1"
    Then the client receives status code of 200
    And the response message should not be empty

  Scenario: Should return error message when invalid input parameters are given
    Given The user request for weather description of city and a country
    When the user calls GET "/weather/ddd/ddd?apikey=2"
    Then the client receives status code of 404
    And the response contains the message "{\"message\":\"Weather information not available at this time\"}"

  Scenario: Should return error message when invalid path parameters are given
    Given The user request for weather description of city and a country
    When the user calls GET "/weather/ddd/?apikey=2"
    Then the client receives status code of 404


  Scenario: Should return error message when api key is missing
    Given The user request for weather description of city and a country
    When the user calls GET "/weather/uk/London"
    Then the client receives status code of 400
    And the response contains the message "{\"message\":\"Missing API Key\"}"

  Scenario: Should return error message when invalid api key is given
    Given The user request for weather description of city and a country
    When the user calls GET "/weather/uk/London?apikey=7"
    Then the client receives status code of 400
    And the response contains the message "{\"message\":\"Invalid API Key\"}"

  Scenario: Should return error message when the api is called more than 5 times
    Given The user request for weather description of city and a country
    When the user calls GET "/weather/uk/London?apikey=1"
    When the user calls GET "/weather/uk/London?apikey=1"
    When the user calls GET "/weather/uk/London?apikey=1"
    When the user calls GET "/weather/uk/London?apikey=1"
    When the user calls GET "/weather/uk/London?apikey=1"
    When the user calls GET "/weather/uk/London?apikey=1"
    Then the client receives status code of 429
    And the response contains the message "{\"message\":\"You have exhausted your API Request Quota\"}"
