package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    static public void setUp() {
        RestAssured.baseURI = "http://localhost:8100/";
    }

}