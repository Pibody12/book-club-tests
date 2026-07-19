package base;

import api.ApiClient;
import com.codeborne.selenide.Configuration;
import inspect.VerificationSteps;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import test_data.FakerTestData;

public class TestBase {

    protected static final ApiClient api = new ApiClient();
    protected static final FakerTestData faker = new FakerTestData();
    protected static final VerificationSteps verificationSteps = new VerificationSteps();

    @BeforeAll
    static public void setUp() {
        RestAssured.baseURI = "https://book-club.qa.guru/";
//        RestAssured.baseURI = "http://localhost:8100/";
        RestAssured.basePath = "/api/v1";

        Configuration.baseUrl = "https://book-club.qa.guru";
//        Configuration.baseUrl = "http://localhost:8100/";
        Configuration.browserSize = "1920x1080";

    }
//    static public void setUp() { RestAssured.baseURI = "http://localhost:8100/";}
}