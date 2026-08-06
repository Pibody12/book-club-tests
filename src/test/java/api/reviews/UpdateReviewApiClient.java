package api.reviews;

import io.qameta.allure.Step;
import models.reviews.update.SuccessfulUpdateReviewResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.reviews.UpdateReviewsSpec.successfulUpdatePutReviewsSpec;

public class UpdateReviewApiClient {

    @Step("Полное обновление отзыва")
    public SuccessfulUpdateReviewResponseModel updatePutReview (String token, Object reviewData, Integer id) {
        return given(baseRequestSpec)
                .header("Authorization", "Bearer " + token)
                .body(reviewData)
                .when()
                .put("/clubs/reviews/" + id + "/")
                .then()
                .spec(successfulUpdatePutReviewsSpec)
                .extract().body().as(SuccessfulUpdateReviewResponseModel.class);
    }

    @Step("Полное обновление отзыва")
    public SuccessfulUpdateReviewResponseModel updateAssessmentReview (String token, Object reviewData, Integer id) {
        return given(baseRequestSpec)
                .header("Authorization", "Bearer " + token)
                .body(reviewData)
                .when()
                .patch("/clubs/reviews/" + id + "/")
                .then()
                .spec(successfulUpdatePutReviewsSpec)
                .extract().body().as(SuccessfulUpdateReviewResponseModel.class);
    }
}
