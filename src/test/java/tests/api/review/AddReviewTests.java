package tests.api.review;

import base.TestBase;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.reviews.AddReviewBodyModel;
import models.reviews.SuccessfulAddReviewsResponseModel;
import models.user.login.LoginBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static test_data.TestData.*;

public class AddReviewTests extends TestBase {

    @Test
    @DisplayName("Добавление комментария создателем клуба")
    public void addOwnerReview () {
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

        verificationSteps.moreThan("id", addReviewsResponse.id(), 0);
        verificationSteps.shouldHaveIntValue("club", addReviewsResponse.club(), clubResponseData.id());
        verificationSteps.shouldHaveIntValue("user id", addReviewsResponse.user().id(),126);
        verificationSteps.shouldHaveValue("username", addReviewsResponse.user().username(), USERNAME);
        verificationSteps.shouldHaveValue("review", addReviewsResponse.review(), faker.fakerDescription);
        verificationSteps.shouldHaveIntValue("assessment", addReviewsResponse.assessment(), faker.fakerAssessment);
        verificationSteps.shouldHaveIntValue("readPages", addReviewsResponse.readPages(), faker.fakerReadPages);
        verificationSteps.isNotEmpty("created", addReviewsResponse.created());
        verificationSteps.isNull("modified", addReviewsResponse.modified());
    }

//    @Test
//    @DisplayName("Добавление комментария создателем клуба")
//    public void addOwnerReview () {
//        String accessToken = api.login.loginUserAndGetAccessToken(
//                new LoginBodyModel(USERNAME, PASSWORD)
//        );
//
//        CreateClubBodyModel clubData = new CreateClubBodyModel(
//                faker.fakerBookTitle,
//                faker.fakerbookAuthors,
//                faker.fakerPublicationYear,
//                faker.fakerDescription,
//                faker.fakerTelegramChatLink
//        );
//        SuccessfulCreateClubResponseModel responseData = api.createClub.createClub(clubData, accessToken);
//
//        AddReviewBodyModel reviewData = new AddReviewBodyModel(
//                responseData.id(),
//                faker.fakerDescription,
//                faker.fakerAssessment,
//                faker.fakerReadPages
//        );
//
//        api.addReviews.addReview(reviewData, accessToken);
//    }
}
