package api.reviews;

import io.qameta.allure.Step;
import models.reviews.add.SuccessfulAddReviewsResponseModel;
import models.reviews.add.WrongReviewDataResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.baseRequestSpec;
import static specs.reviews.AddReviewsSpec.addWrongReviewsSpec;
import static specs.reviews.AddReviewsSpec.successfulAddReviewsSpec;

public class AddReviewApiClient {

    @Step("Добавление отзыва")
    public SuccessfulAddReviewsResponseModel addReview (Object reviewData, String token) {
        return given(baseRequestSpec)
                .header("Authorization", "Bearer " + token)
                .body(reviewData)
                .when()
                .post("/clubs/reviews/")
                .then()
                .spec(successfulAddReviewsSpec)
                .extract().body().as(SuccessfulAddReviewsResponseModel.class);
    }

    @Step("Добавление отзыва")
    public WrongReviewDataResponseModel addWrongReview (Object reviewData, String token) {
        return given(baseRequestSpec)
                .header("Authorization", "Bearer " + token)
                .body(reviewData)
                .when()
                .post("/clubs/reviews/")
                .then()
                .spec(addWrongReviewsSpec)
                .extract().body().as(WrongReviewDataResponseModel.class);
    }
}
