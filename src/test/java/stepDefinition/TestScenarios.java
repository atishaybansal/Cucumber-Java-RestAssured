package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Assert;


public class TestScenarios {
    
	
	RequestSpecification request;
	Response response;
	int code;
	String country;
	int id;
	int error_code;
	String message;
	ArrayList<String> countries;
	int listsize;
	Iterator<String> it;
	
	@Given("Open Weather API is up and running")
	public void open_Weather_API_is_up_and_running() {
		RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
	}
	
	@When("User passes the city name {string} and country code {string} and app id {string} to the open weather api")
	public void user_passes_the_city_name_and_country_code_and_app_id_to_the_open_weather_api(String city_name, String country_code, String app_id) {

		response = given()
                .header("Content-Type","application/json")
                .param("q", city_name,country_code)
                .param("appid", app_id)
               .when()
                 .get("/weather");
		code = response.getStatusCode();
		country = response.getBody().path("sys.country");
       	id = response.getBody().path("sys.id");
	}

	@Then("Verify the response code is {int}")
	public void verify_the_response_code_is(int status_code) { 
       
		Assert.assertEquals(status_code, code);
	}

	@Then("Verify the country code and country id in response body is also {string} and {int} respectively")
	public void verify_the_country_code_and_country_id_in_response_body_is_also_and_respectively(String country_code, int country_id) {
	    
    	Assert.assertEquals(country_code, country);
       	Assert.assertEquals(country_id, id);
	}

	
	@When("User passes the city name {string} and app id {string} to the open weather api")
	public void user_passes_the_city_name_and_app_id_to_the_open_weather_api(String city_name, String app_id) {
		
		response = given()
                .header("Content-Type","application/json")
                .param("q", city_name)
                .param("appid", app_id)
               .when()
                 .get("/find");
		
		countries = response.getBody().path("list.sys.country");
		it = countries.iterator();
		JsonPath js = new JsonPath(response.asString());
		listsize = js.getInt("list.size()");
	}
    
	@Then("Verify the response has a list consisting of multiple countries")
	public void verify_the_response_has_a_list_consisting_of_multiple_countries() {
	    
		while(it.hasNext())
		{
			System.out.println("Country under which the city is present : "+ it.next());
		}
	}
	
	@When("User passes the wrong city name {string} and country code {string} but correct app id {string} to the open weather api")
	public void user_passes_the_wrong_city_name_and_country_code_but_correct_app_id_to_the_open_weather_api(String incorrect_city_name, String incorrect_country_code, String app_id) {
		
		response = given()
                .header("Content-Type","application/json")
                .param("q", incorrect_city_name,incorrect_country_code)
            .param("appid", app_id)
               .when()
                 .get("/weather");
    	error_code = response.getStatusCode();
    	message = response.getBody().path("message");
	}

	@Then("Verify the response code is {int} for wrong city name and country code")
	public void verify_the_response_code_is_for_wrong_city_name_and_country_code(int status_code) {

       	Assert.assertEquals(status_code, error_code);
       	Assert.assertEquals(message,"city not found");
	}

	
	@When("User makes a request with city name {string} and incorrect app id {string} to the open weather api")
	public void user_makes_a_request_with_city_name_and_incorrect_app_id_to_the_open_weather_api(String city_name, String incorrect_app_id) {
		
		response = given()
                .header("Content-Type","application/json")
                .param("q", city_name)
            .param("appid", incorrect_app_id)
               .when()
                 .get("/weather");
    	error_code = response.getStatusCode();
    	message = response.getBody().path("message");
	}

	@Then("Verify the response code is {int} for incorrect app id")
	public void verify_the_response_code_is_for_incorrect_app_id(int status_code) {
		
		Assert.assertEquals(status_code, error_code);
		Assert.assertEquals(message,"Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.");
	}
}
