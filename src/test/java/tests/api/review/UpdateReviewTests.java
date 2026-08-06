package tests.api.review;

import base.TestBase;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.reviews.add.AddReviewBodyModel;
import models.reviews.add.SuccessfulAddReviewsResponseModel;
import models.reviews.update.SuccessfulUpdateReviewResponseModel;
import models.reviews.update.UpdateAssessmentReviewBodyModel;
import models.reviews.update.UpdatePutReviewBodyModel;
import models.user.login.LoginBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static test_data.TestData.PASSWORD;
import static test_data.TestData.USERNAME;

public class UpdateReviewTests extends TestBase {

    @Test
    @DisplayName("Обновление всех полей комментария")
    public void refreshAllReviewTest () {
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

        UpdatePutReviewBodyModel  updatePutReviewBodyModel = new UpdatePutReviewBodyModel(
                clubResponseData.id(),
                "Test Description",
                2,
                12
        );

        SuccessfulUpdateReviewResponseModel updateReviewResponse = api.updateReviews.updatePutReview(accessToken , updatePutReviewBodyModel, addReviewsResponse.id());

        verificationSteps.moreThan("id", updateReviewResponse.id(), 0);
        verificationSteps.shouldHaveIntValue("club", updateReviewResponse.club(), clubResponseData.id());
        verificationSteps.shouldHaveIntValue("user id", updateReviewResponse.user().id(), 126);
        verificationSteps.shouldHaveValue("username", updateReviewResponse.user().username(), USERNAME);
        verificationSteps.shouldHaveValue("review", updateReviewResponse.review(), "Test Description");
        verificationSteps.shouldHaveIntValue("assessment", updateReviewResponse.assessment(), 2);
        verificationSteps.shouldHaveIntValue("readPages", updateReviewResponse.readPages(), 12);
        verificationSteps.isNotEmpty("created", updateReviewResponse.created());
        verificationSteps.isNotEmpty("modified", updateReviewResponse.modified());
    }

    @Test
    @DisplayName("Обновление всех полей комментария")
    public void updateAssessmentReviewTest () {
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

        UpdateAssessmentReviewBodyModel updateAssessmentReview = new UpdateAssessmentReviewBodyModel(
                clubResponseData.id(),
                5
        );

        SuccessfulUpdateReviewResponseModel updateReviewResponse = api.updateReviews.updateAssessmentReview(accessToken , updateAssessmentReview, addReviewsResponse.id());

        verificationSteps.moreThan("id", updateReviewResponse.id(), 0);
        verificationSteps.shouldHaveIntValue("club", updateReviewResponse.club(), clubResponseData.id());
        verificationSteps.shouldHaveIntValue("user id", updateReviewResponse.user().id(), 126);
        verificationSteps.shouldHaveValue("username", updateReviewResponse.user().username(), USERNAME);
        verificationSteps.shouldHaveValue("review", updateReviewResponse.review(), faker.fakerDescription);
        verificationSteps.shouldHaveIntValue("assessment", updateReviewResponse.assessment(), 5);
        verificationSteps.shouldHaveIntValue("readPages", updateReviewResponse.readPages(), faker.fakerReadPages);
        verificationSteps.isNotEmpty("created", updateReviewResponse.created());
        verificationSteps.isNotEmpty("modified", updateReviewResponse.modified());
    }
}
