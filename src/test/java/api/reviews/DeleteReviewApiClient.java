package api.reviews;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.reviews.DeleteReviewSpec.successfulDeleteReviewSpec;

public class DeleteReviewApiClient {

    @Step("Удаление комментария")
    public ValidatableResponse deleteReview(String token, Integer id) {
        return given(baseRequestSpec)
                .headers("Authorization","Bearer " + token)
                .when()
                .delete("/clubs/reviews/" + id + "/")
                .then()
                .spec(successfulDeleteReviewSpec);
    }
}
