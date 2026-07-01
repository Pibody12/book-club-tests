package api;

import io.qameta.allure.Step;
import models.update.SuccessfulUpdatePatchResponseModel;
import models.update.SuccessfulUpdatePutResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.update.UpdateSpec.successfulUpdatePatchResponseSpec;

public class UpdateApiClient {
    @Step("Обновление всех данных пользователя")
    public SuccessfulUpdatePutResponseModel updatePutUser (Object updateData, String token) {
        return given(baseRequestSpec)
                .header("Authorization", token)
                .body(updateData)
                .when()
                .put("/users/me/")
                .then()
                .spec(successfulUpdatePatchResponseSpec)
                .extract().body().as(SuccessfulUpdatePutResponseModel.class);
    }

    @Step("Обновление части данных пользователя")
    public SuccessfulUpdatePatchResponseModel updatePatchUser (Object updateData, String token) {
        return given(baseRequestSpec)
                .header("Authorization", token)
                .body(updateData)
                .when()
                .patch("/users/me/")
                .then()
                .spec(successfulUpdatePatchResponseSpec)
                .extract().body().as(SuccessfulUpdatePatchResponseModel.class);
    }
}
