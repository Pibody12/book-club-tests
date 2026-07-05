package api.club;

import io.qameta.allure.Step;
import models.club.create.SuccessfulCreateClubResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.club.CreateClubSpec.successfulCreateClubResponseSpec;

public class CreateClubApiClient {

    @Step("Создание клуба")
    public SuccessfulCreateClubResponseModel createClub(Object clubData, String token) {
        return given(baseRequestSpec)
                .headers("Authorization", token)
                .body(clubData)
                .when()
                .post("/clubs/")
                .then()
                .spec(successfulCreateClubResponseSpec)
                .extract().body().as(SuccessfulCreateClubResponseModel.class);
    }
}
