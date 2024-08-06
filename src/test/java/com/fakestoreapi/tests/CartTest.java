package com.fakestoreapi.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CartTest extends BaseTest {

    @Test
    public void testGetCartsByUserId() {
        Response response = given()
                .pathParam("userId", USER_ID)
                .when()
                .get("/carts/user/{userId}")
                .then()
                .extract().response();

        if (response.getStatusCode() == 404 || response.jsonPath().getList("$").isEmpty()) {
            test = extent.createTest("testGetCartsByUserId");
            test.fail("Carts list is empty for user ID: " + USER_ID);

        } else {
            test = extent.createTest("testGetCartsByUserId");
            test.info("Response: " + response.asString());
            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertTrue(response.jsonPath().getList("$").size() > 0, "Carts list is empty");
        }
    }
}
