package api;

import io.qameta.allure.Step;
import models.logout.EmptyBodyLogoutResponseModel;
import models.logout.ListWithTwoTokenLogoutResponseModel;
import models.logout.SuccessfulLogoutResponseModel;
import models.logout.WrongRefreshTokenLogoutResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.logout.LogoutSpec.*;

public class LogoutApiClient {

    @Step("Выход из учетки")
    public SuccessfulLogoutResponseModel logoutUser(Object logoutData) {
        return given(baseRequestSpec)
                .body(logoutData)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(logoutSuccessfulResponseSpec)
                .extract().body().as(SuccessfulLogoutResponseModel.class);
    }

    @Step("Выход из учетки c неверным токеном")
    public WrongRefreshTokenLogoutResponseModel  wrongRefreshTokenLogout(Object logoutData) {
        return given(baseRequestSpec)
                .body(logoutData)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(wrongRefreshTokenLogoutResponseSpec)
                .extract().body().as(WrongRefreshTokenLogoutResponseModel.class);
    }

    @Step("Выход из учетки c пустым телом")
    public EmptyBodyLogoutResponseModel emptyBodyLogout(Object logoutData) {
        return given(baseRequestSpec)
                .body(logoutData)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(emptyBodyLogoutResponseSpec)
                .extract().body().as(EmptyBodyLogoutResponseModel.class);
    }

    @Step("Выход из учетки с невалидным типом данных в запросе")
    public ListWithTwoTokenLogoutResponseModel otherTypeDataLogout(Object logoutData) {
        return given(baseRequestSpec)
                .body(logoutData)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(listWithTwoTokenLogoutResponseSpec)
                .extract().body().as(ListWithTwoTokenLogoutResponseModel.class);
    }
}
