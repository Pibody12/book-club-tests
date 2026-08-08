package tests.api.review;

import base.TestBase;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.reviews.add.AddReviewBodyModel;
import models.reviews.add.SuccessfulAddReviewsResponseModel;
import models.reviews.get.GetDeletedReviewResponseModel;
import models.user.login.LoginBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static test_data.TestData.*;

public class DeleteReviewTests extends TestBase {

    @Test
    @DisplayName("Удаление комментария")
    public void deleteReviewTest () {
        String accessToken = api.login.loginUserAndGetAccessToken(
                new LoginBodyModel(USERNAME, PASSWORD)
        );

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                faker.fakerPublicationYear,
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );
        SuccessfulCreateClubResponseModel clubResponseData = api.createClub.createClub(clubData, accessToken);

        AddReviewBodyModel reviewData = new AddReviewBodyModel(
                clubResponseData.id(),
                faker.fakerDescription,
                faker.fakerAssessment,
                faker.fakerReadPages
        );

        SuccessfulAddReviewsResponseModel addReviewsResponse = api.addReviews.addReview(reviewData, accessToken);

        api.deleteReviews.deleteReview(accessToken, addReviewsResponse.id());

        GetDeletedReviewResponseModel getResponse = api.getReviews.getDeletedReview(accessToken, addReviewsResponse.id());

        verificationSteps.shouldHaveValue("detail", NO_REVIEW_MESSAGE, getResponse.detail());
    }
}
