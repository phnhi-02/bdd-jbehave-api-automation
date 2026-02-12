package steps;

import org.jbehave.core.annotations.*;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;

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

    @BeforeScenario
    public void prepareData() {
        RestAssured.reset(); 
        RestAssured.baseURI = "https://dummyjson.com";
        request = RestAssured.given()
                .filter(new AllureRestAssured()) 
                .header("Content-Type", "application/json");

        System.out.println("STARTING...");
    }
    @Given("I can get lists user")
    @Step("Send GET request to /users")
    public void getListsUser() {
        response = request.get("/users");
    }

    @Given("I get user by id $id")
    @Step("Send GET request to /users/{id}")
    public void getUserById(int id) {
        response = request.get("/users/" + id);
    }

    @Given("I can not get user by ID (userId=$id)")
    @Step("Send GET request for invalid user id={id}")
    public void getInvalidUser(int id) {
        response = request.get("/users/" + id);
    }

    @Then("I receive user id $id")
    @Step("Verify returned user id = {id}")
    public void verifyUserIdReturned(int id) {
        response.then().body("id", equalTo(id));
    }

    @Given("I create user with $firstName, $lastName, $age")
    @Step("Create user with firstName={firstName}, lastName={lastName}, age={age}")
    public void createUserWithExample(String firstName, String lastName, int age) {
        expectedFirstName = firstName;
        expectedLastName = lastName;
        expectedAge = age;

        String body = String.format("{\"firstName\":\"%s\", \"lastName\":\"%s\", \"age\":%d}", 
                                    firstName, lastName, age);

        response = request.body(body).post("/users/add");
    }

    @Given("I can create a new user with first name is John, last name is Doe, 30 years old")
    @Step("Create default user John Doe, 30 years old")
    public void createNewUser() {
        expectedFirstName = "John";
        expectedLastName = "Doe";
        expectedAge = 30;

        String body = "{\"firstName\":\"John\", \"lastName\":\"Doe\", \"age\":30}";
        response = request.body(body).post("/users/add");
    }

    @Then("I receive a new user with right info")
    @Step("Verify created user info")
    public void verifyNewUserInfo() {
        response.then()
                .body("firstName", equalTo(expectedFirstName))
                .body("lastName", equalTo(expectedLastName))
                .body("age", equalTo(expectedAge));
    }

    @Given("I can update user (userId=$id)")
    @Step("Update user id={id}")
    public void updateUser(int id) {
        String updateData = "{ \"lastName\": \"Updated Name\" }";
        response = request.body(updateData).put("/users/" + id);
    }

    @Then("user is updated")
    @Step("Verify user updated")
    public void verifyUpdate() {
        response.then().body("lastName", equalTo("Updated Name"));
    }

    @Given("I can delete user (userId=$id)")
    @Step("Delete user id={id}")
    public void deleteUser(int id) {
        response = request.delete("/users/" + id);
    }

    @Then("I can not find user (userId=$id)")
    @Step("Verify user deleted")
    public void verifyDeleted(int id) {
        response.then()
                .statusCode(200)
                .body("isDeleted", equalTo(true));
    }

    @Then("I receive $statusCode status code response")
    @Step("Verify status code = {statusCode}")
    public void verifyStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
    }
}