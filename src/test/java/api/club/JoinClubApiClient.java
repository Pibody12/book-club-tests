package api.club;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.user.SuccessfulRegistrationAndGetResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.club.JoinClubSpec.successfulJoinClubResponseSpec;
import static specs.user.GetSpec.successfulGetResponseSpec;

public class JoinClubApiClient {

    @Step("Вступить в клуб")
    public ValidatableResponse joinClub(String token, Integer clubId) {
        return given(baseRequestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .post("/clubs/" + clubId + "/members/me/")
                .then()
                .spec(successfulJoinClubResponseSpec);
    }
}
