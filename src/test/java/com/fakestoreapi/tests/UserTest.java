package com.fakestoreapi.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserTest extends BaseTest {

    @Test
    public void testGetUserById() {
        Response response = given()
                .pathParam("id", USER_ID)
                .when()
                .get("/users/{id}")
                .then()
                .extract().response();

        if (response.getStatusCode() == 404 || response.asString().equals("null")) {
            test = extent.createTest("testGetUserById");
            test.fail("User ID " + USER_ID + " does not exist.");

        } else {
            test = extent.createTest("testGetUserById");
            test.info("Response: " + response.asString());
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertNotNull(response.jsonPath().getString("username"), "Username is null");
        }
    }
}
