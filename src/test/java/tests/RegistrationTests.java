package tests;


import models.registration.lombok.RegistrationBodyLombokModel;
import models.registration.lombok.RegistrationResponseLombokModel;
import models.registration.pojo.RegistrationBodyPojoModel;
import models.registration.pojo.RegistrationResponsePojoModel;
import models.registration.records.ExistingUserResponseRecordsModel;
import models.registration.records.RegistrationBodyRecordsModel;
import models.registration.records.RegistrationResponseRecordsModel;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationTests{

    String username;
    String password;
    String invalideName;

    @BeforeEach
    public void prepareTestData() {
        Faker faker = new Faker();
        username = faker.name().firstName();
        password = faker.name().lastName();
        invalideName = faker.name().fullName();
    }

    @Test
    public void successfulRegistrationTest_with_pojo(){

        RegistrationBodyPojoModel  data = new RegistrationBodyPojoModel();
        data.setUsername(username);
        data.setPassword(password);

//        With constructor
//        RegistrationBodyPojoModel  data = new RegistrationBodyPojoModel(username, password);

        RegistrationResponsePojoModel registrationResponse = given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://book-club.qa.guru/api/v1/users/register/")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(RegistrationResponsePojoModel.class);

        assertEquals(username, registrationResponse.getUsername());
    }

    @Test
    public void successfulRegistrationTest_with_lombok(){

        RegistrationBodyLombokModel data = new RegistrationBodyLombokModel();
        data.setUsername(username);
        data.setPassword(password);

//        RegistrationBodyLombokModel data1 = new RegistrationBodyLombokModel (username, password);

        RegistrationResponseLombokModel registrationResponse = given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://book-club.qa.guru/api/v1/users/register/")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(RegistrationResponseLombokModel.class);

        assertEquals(username, registrationResponse.getUsername());
    }

    @Test
    public void successfulRegistrationTest_with_records(){

        RegistrationBodyRecordsModel data = new RegistrationBodyRecordsModel(username, password);

        RegistrationResponseRecordsModel registrationResponse = given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://book-club.qa.guru/api/v1/users/register/")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(RegistrationResponseRecordsModel.class);

        assertEquals(username, registrationResponse.username());
    }

    @Test
    public void successfulRegistrationTest_bad_practice(){

        String data = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://book-club.qa.guru/api/v1/users/register/")
                .then()
                .log().all()
                .statusCode(201)
                .body("username", is(username))
                .body("id", notNullValue());
    }

    @Test
    public void existingUser400Test(){

        RegistrationBodyRecordsModel data = new RegistrationBodyRecordsModel(username, password);

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://book-club.qa.guru/api/v1/users/register/")
                .then()
                .log().all()
                .statusCode(201)
                .body("username", is(username))
                .body("id", notNullValue());

        ExistingUserResponseRecordsModel response = given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://book-club.qa.guru/api/v1/users/register/")
                .then()
                .log().all()
                .statusCode(400)
                .extract()
                .as(ExistingUserResponseRecordsModel.class);

        String expectedError = "A user with that username already exists.";
        assertEquals(expectedError, response.username().get(0));
//        assertEquals(expectedError, response.username().getFirst());
    }

    @Test
    public void invalidUserNameTest(){

        RegistrationBodyLombokModel data = new RegistrationBodyLombokModel();
        data.setUsername(username);
        data.setPassword(password);

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://book-club.qa.guru/api/v1/users/register/")
                .then()
                .log().all()
                .statusCode(201);
//                .body("username", is(username))
//                .body("id", notNullValue());
    }

    @Test
    public void unsupportedMediaType415Test(){

        String data = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

        given()
                .log().all()
                .body(data)
                .when()
                .post("https://book-club.qa.guru/api/v1/users/register/")
                .then()
                .log().all()
                .statusCode(201)
                .body("username", is(username))
                .body("id", notNullValue());
    }

    @Test
    public void negativeRegistration500Test(){

        String data = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

        given()
                .body(data)
                .when()
                .post("https://book-club.qa.guru/api/v1/users/register")
                .then()
                .statusCode(201)
                .body("username", is(username))
                .body("id", notNullValue());
    }
}
