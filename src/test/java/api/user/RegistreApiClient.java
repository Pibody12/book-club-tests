package api.user;

import io.qameta.allure.Step;
import models.user.registration.EmptyRegistrationsDataResponseModel;
import models.user.registration.ExistingUserResponseModel;
import models.user.registration.InvalideNameResponseModel;
import models.user.SuccessfulRegistrationAndGetResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.user.RegistrationSpec.*;

public class RegistreApiClient {

    @Step("Регистрация пользователя")
    public SuccessfulRegistrationAndGetResponseModel registerUser(Object registrationData) {
        return given(baseRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successfulRegistrationResponseSpec)
                .extract().body().as(SuccessfulRegistrationAndGetResponseModel.class);
    }

    @Step("Регистрация с данными существующего пользователя")
    public ExistingUserResponseModel registerExistingUser(Object registrationData) {
        return given(baseRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(existingUserResponseSpec)
                .extract().body().as(ExistingUserResponseModel.class);
    }

    @Step("Регистрация с пустыми данными")
    public EmptyRegistrationsDataResponseModel registerEmptyData(Object registrationData) {
        return given(baseRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(emptyRegistrationsDataResponseSpec)
                .extract().body().as(EmptyRegistrationsDataResponseModel.class);
    }

    @Step("Регистрация с невалидным данными")
    public InvalideNameResponseModel registerInvalideData(Object registrationData) {
        return given(baseRequestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(invalideUsernameResponseSpec)
                .extract().body().as(InvalideNameResponseModel.class);
    }








//    public ValidatableResponse login(Object body) {
//        return given(baseRequestSpec)
//                .body(body)
//                .when()
//                .post("/auth/token/")
//                .then();
//    }
//
//    public ValidatableResponse logout(Object body) {
//        return given(baseRequestSpec)
//                .body(body)
//                .when()
//                .post("/auth/logout/")
//                .then()
//                .spec(wrongRefreshTokenLogoutResponseSpec);
//    }
//
//    public ValidatableResponse logout(String refreshToken) {
//        return logout(format("{\"refresh\":\"%s\"}", refreshToken));
//    }
//
//    public ValidatableResponse updateUser(String bearerToken, Object body) {
//        return given(baseRequestSpec)
//                .header("Authorization", bearerToken)
//                .body(body)
//                .when()
//                .put("/users/me/")
//                .then();
//    }
//
//    public ValidatableResponse patchUser(String bearerToken, Object body) {
//        return given(baseRequestSpec)
//                .header("Authorization", bearerToken)
//                .body(body)
//                .when()
//                .patch("/users/me/")
//                .then();
//    }
//
//    public static String bearerToken(String accessToken) {
//        return format("Bearer %s", accessToken);
//    }
}
