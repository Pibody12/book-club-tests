package base;

import api.ApiClient;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBaseClub {

    protected static final ApiClient api = new ApiClient();


    @BeforeAll
    static public void setUp() {
        RestAssured.baseURI = "http://localhost:8100/";
    }
}
