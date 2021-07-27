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
import org.junit.Assert;


public class TestScenarios {
    
    @Given("Current Weather URL is running")
    public void current_Weather_URL_is_running() {
    	RestAssured.baseURI = "https://api.openweathermap.org/data/2.5";
    }
    
    @When("User makes a request with API key and City name and Country code verify the response")
    public void user_makes_a_request_with_API_key_and_City_name_and_Country_code_verify_the_response() {
    	Response response = given()
                .header("Content-Type","application/json")
                .param("q", "London,GB")
            .param("appid", "1cb6ace31e50401f28b864f0b23fdc68")
               .when()
                 .get("/weather");
       	
       	String country = response.getBody().path("sys.country");
       	int code = response.getStatusCode();
       	int id = response.getBody().path("sys.id");
       	Assert.assertEquals(code, 200);
    	Assert.assertEquals(country, "GB");
       	Assert.assertEquals(id, 2019646);
    }

    
    @When("User makes a request with API key and City name verify the response")
    public void user_makes_a_request_with_API_key_and_City_name_verify_the_response() {
    	Response response = given()
                .header("Content-Type","application/json")
                .param("q", "London")
            .param("appid", "1cb6ace31e50401f28b864f0b23fdc68")
               .when()
                 .get("/find");
       	
       	String country1 = response.getBody().path("list[0].sys.country");
    	String country2 = response.getBody().path("list[1].sys.country");
    	String country3 = response.getBody().path("list[2].sys.country");
       	int code = response.getStatusCode();
       Assert.assertEquals(code, 200);
       Assert.assertEquals(country1, "GB");
       Assert.assertEquals(country2, "CA");
       Assert.assertEquals(country3, "US");
    }
    
    
    @When("User makes a request with API key and wrong City name verify the response")
    public void user_makes_a_request_with_API_key_and_wrong_City_name_verify_the_response() {
    	Response response = given()
                .header("Content-Type","application/json")
                .param("q", "Londons,GB")
            .param("appid", "1cb6ace31e50401f28b864f0b23fdc68")
               .when()
                 .get("/weather");
       	
       	int code = response.getStatusCode();
       	Assert.assertEquals(code, 404);
    }

    
    @When("User makes a request with API key and wrong Country code verify the response")
    public void user_makes_a_request_with_API_key_and_wrong_Country_code_verify_the_response() {
    	Response response = given()
                .header("Content-Type","application/json")
                .param("q", "London,GS")
            .param("appid", "1cb6ace31e50401f28b864f0b23fdc68")
               .when()
                 .get("/weather");
       	
       	int code = response.getStatusCode();
       	Assert.assertEquals(code, 404);
    }

    
    @When("User makes a request with wrong API key verify the response")
    public void user_makes_a_request_with_wrong_API_key_verify_the_response() {
    	Response response = given()
                .header("Content-Type","application/json")
                .param("q", "London,GB")
            .param("appid", "1cb6ace31e50401f28b864f0b23fdc69")
               .when()
                 .get("/weather");
       	
    	String message = response.getBody().path("message");
       	int code = response.getStatusCode();
       	Assert.assertEquals(code, 401);
       	Assert.assertEquals(message, "Invalid API key. Please see http://openweathermap.org/faq#error401 for more info.");
    }
}
