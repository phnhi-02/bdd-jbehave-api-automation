package steps;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;

public class UserApiSteps {

    private Response response;
    private RequestSpecification request;

    private String expectedFirstName;
    private String expectedLastName;
    private int expectedAge;
    
    private static int totalScenarios = 0;
    private static int passedScenarios = 0;
    private static int failedScenarios = 0;


    @BeforeScenario  //chạy trước mỗi scenario
    public void prepareData() {
    	totalScenarios++;
        System.out.println("--- [START] ---");
        RestAssured.baseURI = "https://dummyjson.com";
        request = RestAssured.given()
                .header("Content-Type", "application/json");
    }

    @Given("I can get lists user")
    public void getListsUser() {
        response = request.get("/users");
    }

    @Given("I get user by id $id")
    public void getUserById(int id) {
        response = request.get("/users/" + id);
    }

    @Then("I receive user id $id")
    public void verifyUserIdReturned(int id) {
        response.then().body("id", equalTo(id));
    }

    @Given("I can not get user by ID (userId=$id)")
    public void getInvalidUser(int id) {
        response = request.get("/users/" + id);
    }
    
    @Given("I can create a new user with first name is John, last name is Doe, 30 years old")
    public void createNewUser() {
    	expectedFirstName = "John";
    	expectedLastName = "Doe";
    	expectedAge = 30;
    	
    	String body = "{"
                + "\"firstName\":\"" + "John" + "\","
                + "\"lastName\":\"" + "Doe" + "\","
                + "\"age\":" + "30"
                + "}";
    	response = request.body(body).post("/users/add");
    }

    @Given("I create user with $firstName, $lastName, $age")
    public void createUserWithExample(String firstName, String lastName, int age) {

        expectedFirstName = firstName;
        expectedLastName = lastName;
        expectedAge = age;

        String body = "{"
                + "\"firstName\":\"" + firstName + "\","
                + "\"lastName\":\"" + lastName + "\","
                + "\"age\":" + age
                + "}";

        response = request
                .body(body)
                .post("/users/add");
    }

    @Then("I receive a new user with right info")
    public void verifyNewUserInfo() {

        response.then()
                .body("firstName", equalTo(expectedFirstName))
                .body("lastName", equalTo(expectedLastName))
                .body("age", equalTo(expectedAge));
    }

    @Given("I can update user (userId=$id)")
    public void updateUser(int id) {

        String updateData = "{ \"lastName\": \"Updated Name\" }";

        response = request
                .body(updateData)
                .put("/users/" + id);
    }

    @Then("user is updated")
    public void verifyUpdate() {

        response.then()
                .body("lastName", equalTo("Updated Name"));
    }

    @Given("I can delete user (userId=$id)")
    public void deleteUser(int id) {

        response = request.delete("/users/" + id);
    }

    @Then("I can not find user (userId=$id)")
    public void verifyDeleted(int id) {

        response.then()
                .statusCode(200)
                .body("isDeleted", equalTo(true));
    }

    @Then("I receive $statusCode status code response")
    public void verifyStatusCode(int statusCode) {

        response.then().statusCode(statusCode);
    }
    
    @AfterScenario
    public void cleanUp() {
        RestAssured.reset();
    }

    @AfterScenario(uponOutcome = AfterScenario.Outcome.SUCCESS)
    public void afterSuccessScenario() {
        passedScenarios++;
    }
    
    @AfterScenario(uponOutcome = AfterScenario.Outcome.FAILURE)
    public void afterFailedScenario() {
        failedScenarios++;
    }
    
    @AfterStories
    public void printSummaryReport() {

        System.out.println("\nTEST SUMMARY");
        System.out.println("Total Scenarios : " + totalScenarios);
        System.out.println("Passed          : " + passedScenarios);
        System.out.println("Failed          : " + failedScenarios);
       
    }


}
