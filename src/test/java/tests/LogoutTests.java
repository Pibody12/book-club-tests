package tests;

import base.TestBase;
import models.login.LoginBodyModel;
import models.logout.EmptyBodyLogoutResponseModel;
import models.logout.ListWithTwoTokenLogoutResponseModel;
import models.logout.SuccessfulLogoutResponseModel;
import models.logout.WrongRefreshTokenLogoutResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_data.FakerTestData;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.login.LoginSpec.loginRequestSpec;
import static specs.login.LoginSpec.successfulLoginResponseSpec;
import static specs.logout.LogoutSpec.*;
import static specs.registration.RegistrationSpec.registrationRequestSpec;
import static specs.registration.RegistrationSpec.successfulRegistrationResponseSpec;

public class LogoutTests  extends TestBase {

    FakerTestData fakerTestData = new FakerTestData();

    String wrongRefreshToken = "wrongRefrshToken";
    String[] massiveToken = {"token1", "token2"};

    @Test
    @DisplayName("Успешный логаут")
    public void successfulLogoutTest() {

        RegistrationBodyModel registrationData = new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        SuccessfulRegistrationResponseModel firstRegistrationResponse = given(registrationRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract().body().as(SuccessfulRegistrationResponseModel.class);

        LoginBodyModel loginData = new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        String refreshToken = given(loginRequestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("refresh");

        String logoutData = format("{\"refresh\":\"%s\"}", refreshToken);

        SuccessfulLogoutResponseModel logoutResponse =  given(logoutRequestSpec)
            .body(logoutData)
            .when()
            .post("/auth/logout/")
            .then()
            .spec(logoutSuccessfulResponseSpec)
            .extract().body().as(SuccessfulLogoutResponseModel.class);
    }

    @Test
    @DisplayName("Ошибка в токене логаут")
    public void invalideRefreshTokenLogoutTest() {

        WrongRefreshTokenLogoutResponseModel logoutResponse =  given(logoutRequestSpec)
            .body(wrongRefreshToken)
            .when()
            .post("/auth/logout/")
            .then()
            .spec(wrongRefreshTokenLogoutResponseSpec)
            .extract().body().as(WrongRefreshTokenLogoutResponseModel.class);

        String expectedError = "JSON parse error - unexpected character: line 1 column 1 (char 0)";
        String actualError = logoutResponse.detail();
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test
    @DisplayName("Пустое тело запроса логаут")
    public void emptyBodyResponseLogoutTest() {

        EmptyBodyLogoutResponseModel logoutResponse =  given(logoutRequestSpec)
            .body("")
            .when()
            .post("/auth/logout/")
            .then()
            .spec(emptyBodyLogoutResponseSpec)
            .extract().body().as(EmptyBodyLogoutResponseModel.class);

        String expectedError = "This field is required.";
        String actualError = logoutResponse.refresh().getFirst();
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test
    @DisplayName("Пустое тело запроса логаут")
    public void invalideTokenResponseLogoutTest() {

        EmptyBodyLogoutResponseModel logoutResponse =  given(logoutRequestSpec)
            .body("")
            .when()
            .post("/auth/logout/")
            .then()
            .spec(emptyBodyLogoutResponseSpec)
            .extract().body().as(EmptyBodyLogoutResponseModel.class);

        String expectedError = "This field is required.";
        String actualError = logoutResponse.refresh().getFirst();
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test
    @DisplayName("Пустое тело запроса логаут")
    public void listWithTwoTokenResponseLogoutTest() {

        ListWithTwoTokenLogoutResponseModel logoutResponse =  given(logoutRequestSpec)
                .body(massiveToken)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(listWithTwoTokenLogoutResponseSpec)
                .extract().body().as(ListWithTwoTokenLogoutResponseModel.class);

        String expectedError = "Invalid data. Expected a dictionary, but got list.";
        String actualError = logoutResponse.nonFieldErrors().getFirst();
        assertThat(actualError).isEqualTo(expectedError);
    }
}