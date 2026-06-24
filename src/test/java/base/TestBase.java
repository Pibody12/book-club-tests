package base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    static public void setUp() {
        RestAssured.baseURI = "https://book-club.qa.guru/";
    }

}