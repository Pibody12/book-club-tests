package tests;


import base.TestBase;
import models.login.*;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_data.FakerTestData;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.BaseSpec.baseRequestSpec;
import static specs.login.LoginSpec.*;
import static specs.registration.RegistrationSpec.successfulRegistrationResponseSpec;

public class LoginTests extends TestBase {

    FakerTestData fakerTestData = new FakerTestData();

    String wrongPassword = "string666";

    @Test
    @DisplayName("Успешная авторизация")
    public void successfulLoginTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        step("Регистрация нового пользователя", () ->{
        SuccessfulRegistrationResponseModel firstRegistrationResponse = given(baseRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract().body().as(SuccessfulRegistrationResponseModel.class);
        });

        step("Авторизация и проверка ответа", () ->{
            LoginBodyModel loginData = new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

            SuccessfulLoginResponseModel loginResponse = given(baseRequestSpec)
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
        });
    }

    @Test
    @DisplayName("Ошибка в пароле при авторизации")
    public void wrongCredentialsLoginTest(){

        step("Регистрация нового пользователя", () -> {
            RegistrationBodyModel registrationData = new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

            SuccessfulRegistrationResponseModel firstRegistrationResponse = given(baseRequestSpec)
                    .body(registrationData)
                    .when()
                    .post("/users/register/")
                    .then()
                    .spec(successfulRegistrationResponseSpec)
                    .extract().body().as(SuccessfulRegistrationResponseModel.class);
        });

        step("Авторизация с неправильным паролем и проверка ответа", () -> {
            LoginBodyModel loginData = new LoginBodyModel(fakerTestData.fakerUserFirstName, wrongPassword);

            WrongCredetialsLoginResponseModel loginResponse = given(baseRequestSpec)
                    .body(loginData)
                    .when()
                    .post("/auth/token/")
                    .then()
                    .spec(wrongCredentialsLoginResponseSpec)
                    .extract().body().as(WrongCredetialsLoginResponseModel.class);

            String expectedDetailError = "Invalid username or password.";
            String actualDetailError = loginResponse.detail();
            assertThat(actualDetailError).isEqualTo(expectedDetailError);
        });
    }

    @Test
    @DisplayName("Авторизация с пустыми данными")
    public void emptyCredentialsLoginTest(){

        step("Авторизация с пустыми кредами и проверка ответа", () -> {
            LoginBodyModel loginEmptyData = new LoginBodyModel("", "");

            EmptyCredentialsResponseModel loginResponse = given(baseRequestSpec)
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
        });
    }

    @Test
    @DisplayName("Авторизация с пустым телом в запросе")
    public void emptyBodyLoginTest(){

        step("Авторизация с пустым телом в запросе и проверка ответа", () -> {
            LoginEmptyBodyModel loginData = new LoginEmptyBodyModel();

            EmptyBodyResponseModel loginResponse = given(baseRequestSpec)
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
        });
    }
}
