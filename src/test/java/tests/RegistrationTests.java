package tests;

import base.TestBase;
import models.registration.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_data.FakerTestData;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.BaseSpec.baseRequestSpec;
import static specs.registration.RegistrationSpec.*;

public class RegistrationTests extends TestBase {

    FakerTestData fakerTestData = new FakerTestData();

    @Test
    @DisplayName("Успешная регистрация")
    public void successfulRegistrationTest(){

        step("Регистрация нового пользователя и проверка ответа", () -> {
                    RegistrationBodyModel registrationData = new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

                    SuccessfulRegistrationResponseModel registrationResponse = given(baseRequestSpec)
                            .body(registrationData)
                            .when()
                            .post("/users/register/")
                            .then()
                            .spec(successfulRegistrationResponseSpec)
                            .extract().body().as(SuccessfulRegistrationResponseModel.class);

            assertThat(registrationResponse.id()).isGreaterThan(0);
            assertThat(registrationResponse.username()).isEqualTo(fakerTestData.fakerUserFirstName);
            assertThat(registrationResponse.firstName()).isEqualTo("");
            assertThat(registrationResponse.lastName()).isEqualTo("");
            assertThat(registrationResponse.email()).isEqualTo("");

            String ipAddrRegexp = "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}"
                    + "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$";
            assertThat(registrationResponse.remoteAddr()).matches(ipAddrRegexp);
        });
    }

    @Test
    @DisplayName("Повторная регистрация с существующими данными")
    public void existingUserWrongRegistrationTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        step("Регистрация нового пользователя", () -> {
                SuccessfulRegistrationResponseModel firstRegistrationResponse = given(baseRequestSpec)
                        .body(registrationData)
                        .when()
                        .post("/users/register/")
                        .then()
                        .spec(successfulRegistrationResponseSpec)
                        .extract().body().as(SuccessfulRegistrationResponseModel.class);

                assertThat(firstRegistrationResponse.username()).isEqualTo(fakerTestData.fakerUserFirstName);
        });

        step("Регистрация пользователя с уже существующими данными и проверка ответа", () -> {
            ExistingUserResponseModel secondRegistrationResponse = given(baseRequestSpec)
                    .body(registrationData)
                    .when()
                    .post("/users/register/")
                    .then()
                    .spec(existingUserResponseSpec)
                    .extract().body().as(ExistingUserResponseModel.class);

            String expectedError = "A user with that username already exists.";
            String actualError = secondRegistrationResponse.username().getFirst();
            assertThat(actualError).isEqualTo(expectedError);
        });
    }

    @Test
    @DisplayName("Регистрация с пустыми данными")
    public void emptyRegistrationsDataRegistrationTest(){
        RegistrationBodyModel registrationData = new RegistrationBodyModel("", "");

        step("Регистрация пользователя с пустыми данными и проверка ответа", () -> {
            EmptyRegistrationsDataResponseModel registrationResponse = given(baseRequestSpec)
                    .body(registrationData)
                    .when()
                    .post("/users/register/")
                    .then()
                    .spec(emptyRegistrationsDataResponseSpec)
                    .extract().body().as(EmptyRegistrationsDataResponseModel.class);

            String expectedError = "This field may not be blank.";
            String actualUsernameError = registrationResponse.username().getFirst();
            String actualPasswordError = registrationResponse.username().getLast();
            assertThat(actualUsernameError).isEqualTo(expectedError);
            assertThat(actualPasswordError).isEqualTo(expectedError);
        });
    }

    @Test
    @DisplayName("Регистрация с невалидным именем")
    public void invalideUsernameRegistrationTest(){
        RegistrationBodyModel registrationData = new RegistrationBodyModel(fakerTestData.fakerInvalideName, fakerTestData.fakerPassword);

        step("Регистрация пользователя с невалидным данными и проверка ответа", () -> {
            InvalideNameResponseModel registrationResponse = given(baseRequestSpec)
                    .body(registrationData)
                    .when()
                    .post("/users/register/")
                    .then()
                    .spec(invalideUsernameResponseSpec)
                    .extract().body().as(InvalideNameResponseModel.class);

            String expectedError = "Enter a valid username. This value may contain only letters, numbers, and @/./+/-/_ characters.";
            String actualError = registrationResponse.username().getFirst();
            assertThat(actualError).isEqualTo(expectedError);
        });
    }
}
