Feature: Open Weather API Test Scenarios


Scenario: To fetch the current weather of city as London and country code as GB

		Given Open Weather API is up and running
    When User passes the city name "London" and country code "GB" and app id "1cb6ace31e50401f28b864f0b23fdc68" to the open weather api
    Then Verify the response code is 200
    And Verify the country code and country id in response body is also "GB" and 2019646 respectively
    
Scenario: To verify that the city is not unique and present under multiple countries

		Given Open Weather API is up and running
    When User passes the city name "London" and app id "1cb6ace31e50401f28b864f0b23fdc68" to the open weather api
    Then Verify the response has a list consisting of multiple countries
    
Scenario: To verify the response when city name and country code is not correct

		Given Open Weather API is up and running
    When User passes the wrong city name "ABCD" and country code "GS" but correct app id "1cb6ace31e50401f28b864f0b23fdc68" to the open weather api
    Then Verify the response code is 404 for wrong city name and country code
    
Scenario: To verify the response when app id is not correct

		Given Open Weather API is up and running
    When User makes a request with city name "London" and incorrect app id "1cb6ace31e50401f28b864f0b23fdc69" to the open weather api
    Then Verify the response code is 401 for incorrect app id
    
    