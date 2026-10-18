package api.club;

import io.qameta.allure.Step;
import models.club.update.SuccessfulUpdateClubResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.club.UpdateClubSpec.successfulUpdateCLubResponseSpec;

public class UpdateClubApiClient {

    @Step("Изминение клуба")
    public SuccessfulUpdateClubResponseModel updateClub(Object clubData, String token, Integer id) {
        return given(baseRequestSpec)
                .headers("Authorization","Bearer " + token)
                .body(clubData)
                .when()
                .put("/clubs/" + id + "/")
                .then()
                .spec(successfulUpdateCLubResponseSpec)
                .extract().body().as(SuccessfulUpdateClubResponseModel.class);
    }
}
