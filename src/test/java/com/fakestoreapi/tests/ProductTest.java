package com.fakestoreapi.tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProductTest extends BaseTest {

    @Test
    public void testGetProductsFromCarts() {
        Response cartsResponse = given()
                .pathParam("userId", USER_ID)
                .when()
                .get("/carts/user/{userId}")
                .then()
                .extract().response();

        if (cartsResponse.getStatusCode() == 404 || cartsResponse.jsonPath().getList("$").isEmpty()) {
            test = extent.createTest("testGetProductsFromCarts");
            test.fail("Carts list is empty for user ID: " + USER_ID);

        } else {
            test = extent.createTest("testGetProductsFromCarts");
            test.info("Carts Response: " + cartsResponse.asString());
            Assert.assertEquals(cartsResponse.getStatusCode(), 200);

            for (Object cart : cartsResponse.jsonPath().getList("$")) {
                int cartId = ((Map<String, Integer>) cart).get("id");

                Response productsResponse = given()
                        .pathParam("cartId", cartId)
                        .when()
                        .get("/carts/{cartId}")
                        .then()
                        .extract().response();

                test.info("Products Response for Cart ID " + cartId + ": " + productsResponse.asString());
                Assert.assertEquals(productsResponse.getStatusCode(), 200);
            }
        }
    }
}
