package api.reviews;

import io.qameta.allure.Step;
import models.reviews.get.GetDeletedReviewResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.reviews.GetReviewSpec.getDeletedReviewsResponseSpec;

public class GetReviewApiClient {

    @Step("Проверка наличия удаленного комментария")
    public GetDeletedReviewResponseModel getDeletedReview(String token, Integer id) {
        return given(baseRequestSpec)
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("/clubs/reviews/" + id + "/")
                .then()
                .spec(getDeletedReviewsResponseSpec)
                .extract().body().as(GetDeletedReviewResponseModel.class);
    }
}
