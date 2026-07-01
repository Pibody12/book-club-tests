package base;

import api.ApiClient;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import test_data.FakerTestData;

public class TestBase {

    protected static final ApiClient api = new ApiClient();
    protected static final FakerTestData fakerTestData = new FakerTestData();

    @BeforeAll
    static public void setUp() {
        RestAssured.baseURI = "https://book-club.qa.guru/";
    }
}