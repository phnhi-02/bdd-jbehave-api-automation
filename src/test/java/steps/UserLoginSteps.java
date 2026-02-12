package steps;

import org.jbehave.core.annotations.*;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;

public class UserLoginSteps {

    private Response response;
    private RequestSpecification request;

    @BeforeScenario
    public void prepareData() {
        RestAssured.reset(); 
        RestAssured.baseURI = "https://dummyjson.com";
        request = RestAssured.given()
                .filter(new AllureRestAssured()) 
                .header("Content-Type", "application/json");

        System.out.println("STARTING LOGIN SCENARIO");
    }

    @Given("I login with username $user, password $pass")
    @Step("Login with username={user}")
    public void loginUser(String user, String pass) {
        String loginBody = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", user, pass);
        response = request.body(loginBody).post("/auth/login");
    }

    @Then("I receive a valid access token")
    @Step("Verify access token is present")
    public void verifyToken() {
        response.then().body("accessToken", notNullValue());
    }

    @Then("I receive an error message $message")
    @Step("Verify error message = {message}")
    public void verifyErrorMessage(String message) {
        response.then().body("message", equalTo(message));
    }
}