package api.user;

import io.qameta.allure.Step;
import models.user.SuccessfulRegistrationAndGetResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.user.GetSpec.successfulGetResponseSpec;

public class GetUserApiClient {

    @Step("Получить пользователя")
    public SuccessfulRegistrationAndGetResponseModel getUser(String token) {
        return given(baseRequestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/users/me/")
                .then()
                .spec(successfulGetResponseSpec)
                .extract().body().as(SuccessfulRegistrationAndGetResponseModel.class);
    }
}
