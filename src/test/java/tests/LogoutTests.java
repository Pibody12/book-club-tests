package tests;

import base.TestBase;
import models.login.LoginBodyModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static specs.login.LoginSpec.loginRequestSpec;
import static specs.login.LoginSpec.successfulLoginResponseSpec;

public class LogoutTests  extends TestBase {

    String username = "Tim42";
    String password = "string42";

    @Test
    public void successfulLogoutTest() {
        LoginBodyModel loginData = new LoginBodyModel(username, password);

        String refreshToken = given(loginRequestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successfulLoginResponseSpec)
                .extract().path("refresh");

        // todo переделать в модельки и спеки
        String logoutData = format("{\"refresh\":\"%s\"}", refreshToken);

        given()
            .log().all()
            .contentType(JSON)
            .body(logoutData)
//            .formParam("refresh", refreshToken)
            .basePath("/api/v1")
            .when()
            .post("/auth/logout/")
            .then()
            .log().all()
            .statusCode(200)
        ;
        // todo проверить пустой ответ
    }

    //todo еще негативные тесты
    //todo Тест на 400 ошибку из за передачи фигни в параметре токен
//    @Test
//    public void successfulLogoutTest() {
//        LoginBodyModel loginData = new LoginBodyModel(username, password);
//
//        String refreshToken = given(loginRequestSpec)
//                .body(loginData)
//                .when()
//                .post("/auth/token/")
//                .then()
//                .spec(successfulLoginResponseSpec)
//                .extract().path("refresh");
//
////        String logoutData = "{\"refresh\":\"" + refreshToken + "\"}";
//
//        given()
//            .log().all()
//            .contentType(JSON)
////            .body(logoutData)
//            .formParam("refresh", refreshToken)
//            .basePath("/api/v1")
//            .when()
//            .post("/auth/logout/")
//            .then()
//            .log().all()
//            .statusCode(200)
//        ;
//
//    }
}