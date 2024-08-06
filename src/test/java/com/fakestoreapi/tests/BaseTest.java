package com.fakestoreapi.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.RestAssured;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected static final ExtentReports extent = new ExtentReports();
    protected static ExtentTest test;
    protected static final int USER_ID = 93; // Change this ID to test with different users

    @BeforeSuite
    public void setup() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent.html");
        extent.attachReporter(sparkReporter);
        RestAssured.baseURI = "https://fakestoreapi.com";
    }

    @AfterSuite
    public void tearDown() {
        extent.flush();
    }
}
