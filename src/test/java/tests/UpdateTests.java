package tests;

import base.TestBase;
import models.login.LoginBodyModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import models.update.SuccessfulUpdatePatchResponseModel;
import models.update.UpdatePatchBodyModel;
import models.update.UpdatePutBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_data.FakerTestData;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.login.LoginSpec.loginRequestSpec;
import static specs.login.LoginSpec.successfulLoginResponseSpec;
import static specs.registration.RegistrationSpec.registrationRequestSpec;
import static specs.registration.RegistrationSpec.successfulRegistrationResponseSpec;
import static specs.update.UpdateSpec.successfulUpdatePatchResponseSpec;
import static specs.update.UpdateSpec.updateRequestSpec;

public class UpdateTests extends TestBase {

    FakerTestData fakerTestData = new FakerTestData();

    @Test
    @DisplayName("Обновление всех данных пользователя")
    public void successfulPutUpdateTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        SuccessfulRegistrationResponseModel registrationResponse = given(registrationRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract().body().as(SuccessfulRegistrationResponseModel.class);

        LoginBodyModel loginData = new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        String accessToken = given(loginRequestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("access");

        String token = format("Bearer %s", accessToken);

        UpdatePutBodyModel updateData = new UpdatePutBodyModel(
                fakerTestData.fakerUserFirstName, "Tim", "Drob", "test@test.com");

        SuccessfulUpdatePatchResponseModel updateResponse = given(updateRequestSpec)
                .header("Authorization", token)
                .body(updateData)
                .when()
                .put("/users/me/")
                .then()
                .spec(successfulUpdatePatchResponseSpec)
                .extract().body().as(SuccessfulUpdatePatchResponseModel.class);

        assertThat(updateResponse.id()).isGreaterThan(0);
        assertThat(updateResponse.username()).isEqualTo(fakerTestData.fakerUserFirstName);
        assertThat(updateResponse.firstName()).isEqualTo("Tim");
        assertThat(updateResponse.lastName()).isEqualTo("Drob");
        assertThat(updateResponse.email()).isEqualTo("test@test.com");
    }

    @Test
    @DisplayName("Обновление части данных пользователя")
    public void successfulPatchUpdateTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        SuccessfulRegistrationResponseModel registrationResponse = given(registrationRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract().body().as(SuccessfulRegistrationResponseModel.class);

        LoginBodyModel loginData = new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        String accessToken = given(loginRequestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("access");

        String token = format("Bearer %s", accessToken);

        UpdatePatchBodyModel updateData = new UpdatePatchBodyModel("Tim42", "Drob42");

        SuccessfulUpdatePatchResponseModel updateResponse = given(updateRequestSpec)
                .header("Authorization", token)
                .body(updateData)
                .when()
                .patch("/users/me/")
                .then()
                .spec(successfulUpdatePatchResponseSpec)
                .extract().body().as(SuccessfulUpdatePatchResponseModel.class);

        assertThat(updateResponse.id()).isGreaterThan(0);
        assertThat(updateResponse.username()).isEqualTo(fakerTestData.fakerUserFirstName);
        assertThat(updateResponse.firstName()).isEqualTo("Tim42");
        assertThat(updateResponse.lastName()).isEqualTo("Drob42");
    }
}
