Feature: Open Weather API Test Scenarios


Scenario: To fetch the current weather of city as London and country code as GB

		Given Current Weather URL is running
    When User makes a request with API key and City name and Country code verify the response
    
Scenario: To test that the city name is not unique and present in multiple countries

		Given Current Weather URL is running
    When User makes a request with API key and City name verify the response
    
Scenario: To verify the response when city name is not correct

		Given Current Weather URL is running
    When User makes a request with API key and wrong City name verify the response
    
Scenario: To verify the response when country code is not correct

		Given Current Weather URL is running
    When User makes a request with API key and wrong Country code verify the response
    
Scenario: To verify the response when app id is not correct

		Given Current Weather URL is running
    When User makes a request with wrong API key verify the response
    