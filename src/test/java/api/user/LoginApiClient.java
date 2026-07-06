package api.user;

import io.qameta.allure.Step;
import models.user.login.EmptyBodyResponseModel;
import models.user.login.EmptyCredentialsResponseModel;
import models.user.login.SuccessfulLoginResponseModel;
import models.user.login.WrongCredetialsLoginResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.user.LoginSpec.*;

public class LoginApiClient {

    @Step("Авторизация пользователя")
    public SuccessfulLoginResponseModel loginUser(Object userCredetianals) {
        return given(baseRequestSpec)
                .body(userCredetianals)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().body().as(SuccessfulLoginResponseModel.class);
    }

    @Step("Авторизация пользователя и получение refresh токена")
    public String loginUserAndGetRefreshToken(Object userCredetianals) {
        return given(baseRequestSpec)
                .body(userCredetianals)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("refresh");
    }


    @Step("Авторизация пользователя и получение access токена")
    public String loginUserAndGetAccessToken(Object userCredetianals) {
        return given(baseRequestSpec)
                .body(userCredetianals)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("access");
    }

    @Step("Авторизация с неправильным паролем")
    public WrongCredetialsLoginResponseModel wrongPasswordLogin(Object userCredetianals) {
        return given(baseRequestSpec)
                .body(userCredetianals)
                .when()
                .post("/auth/token/")
                .then()
                .spec(wrongCredentialsLoginResponseSpec)
                .extract().body().as(WrongCredetialsLoginResponseModel.class);
    }

    @Step("Авторизация с пустыми данными")
    public EmptyCredentialsResponseModel  emptyPasswordLogin(Object loginEmptyData) {
        return given(baseRequestSpec)
                .body(loginEmptyData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(emptyCredentialsLoginResponseSpec)
                .extract().body().as(EmptyCredentialsResponseModel.class);
    }

    @Step("Авторизация с пустым телом в запросе")
    public EmptyBodyResponseModel emptyBodyLogin(Object loginData) {
        return given(baseRequestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(emptyBodyLoginResponseSpec)
                .extract().body().as(EmptyBodyResponseModel.class);
    }
}
