package api.club;

import io.qameta.allure.Step;
import models.club.get.GetDeletedClubResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.club.GetClubSpec.getDeletedCLubResponseSpec;

public class GetClubApiClient {

    @Step("Проверка наличия удаленного клуба")
    public GetDeletedClubResponseModel getDeletedClub(String token, Integer id) {
        return given(baseRequestSpec)
                .headers("Authorization", token)
                .when()
                .get("/clubs/" + id + "/")
                .then()
                .spec(getDeletedCLubResponseSpec)
                .extract().body().as(GetDeletedClubResponseModel.class);
    }
}
