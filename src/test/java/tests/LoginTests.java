package tests;


import base.TestBase;
import models.login.*;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.Test;
import test_data.FakerTestData;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.login.LoginSpec.*;
import static specs.registration.RegistrationSpec.registrationRequestSpec;
import static specs.registration.RegistrationSpec.successfulRegistrationResponseSpec;

public class LoginTests extends TestBase {

    FakerTestData fakerTestData = new FakerTestData();

    String wrongPassword = "string666";

    @Test
    public void successfulLoginTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        SuccessfulRegistrationResponseModel firstRegistrationResponse = given(registrationRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract().body().as(SuccessfulRegistrationResponseModel.class);

        LoginBodyModel loginData = new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        SuccessfulLoginResponseModel loginResponse = given(loginRequestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().body().as(SuccessfulLoginResponseModel.class);

        String expectedTokenPath = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
        String actualAccess = loginResponse.access();
        String actualRefresh = loginResponse.refresh();

        assertThat(actualAccess).startsWith(expectedTokenPath);
        assertThat(actualRefresh).startsWith(expectedTokenPath);
        assertThat(actualAccess).isNotEqualTo(actualRefresh);
    }

    @Test
    public void wrongCredentialsLoginTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        SuccessfulRegistrationResponseModel firstRegistrationResponse = given(registrationRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract().body().as(SuccessfulRegistrationResponseModel.class);

        LoginBodyModel loginData = new LoginBodyModel(fakerTestData.fakerUserFirstName, wrongPassword);

        WrongCredetialsLoginResponseModel loginResponse = given(loginRequestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(wrongCredentialsLoginResponseSpec)
                .extract().body().as(WrongCredetialsLoginResponseModel.class);

        String expectedDetailError = "Invalid username or password.";
        String actualDetailError = loginResponse.detail();
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    public void emptyCredentialsLoginTest(){

        LoginBodyModel loginEmptyData = new LoginBodyModel("", "");

        EmptyCredentialsResponseModel loginResponse = given(loginRequestSpec)
                .body(loginEmptyData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(emptyCredentialsLoginResponseSpec)
                .extract().body().as(EmptyCredentialsResponseModel.class);

        String expectedError = "This field may not be blank.";
        String actualUsernameError = loginResponse.username().getFirst();
        String actualPasswordError = loginResponse.password().getFirst();
        assertThat(actualUsernameError).isEqualTo(expectedError);
        assertThat(actualPasswordError).isEqualTo(expectedError);
    }

    @Test
    public void emptyBodyLoginTest(){

        LoginEmptyBodyModel loginData = new LoginEmptyBodyModel();

        EmptyBodyResponseModel loginResponse = given(loginRequestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(emptyBodyLoginResponseSpec)
                .extract().body().as(EmptyBodyResponseModel.class);

        String expectedError = "This field is required.";
        String actualUsernameError = loginResponse.username().getFirst();
        String actualPasswordError = loginResponse.password().getFirst();
        assertThat(actualUsernameError).isEqualTo(expectedError);
        assertThat(actualPasswordError).isEqualTo(expectedError);
    }
}
