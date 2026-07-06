package api.club;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.club.DeleteClubSpec.successfulDeleteCLubResponseSpec;

public class DeleteClubApiClient {

    @Step("Изминение клуба")
    public ValidatableResponse deleteClub(String token, Integer id) {
        return given(baseRequestSpec)
                .headers("Authorization", token)
                .when()
                .delete("/clubs/" + id + "/")
                .then()
                .spec(successfulDeleteCLubResponseSpec);
    }
}
