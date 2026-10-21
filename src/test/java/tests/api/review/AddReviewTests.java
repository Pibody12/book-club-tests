package tests.api.review;

import base.TestBase;
import io.qameta.allure.Story;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.reviews.add.AddReviewBodyModel;
import models.reviews.add.SuccessfulAddReviewsResponseModel;
import models.reviews.add.WrongReviewDataResponseModel;
import models.user.SuccessfulRegistrationAndGetResponseModel;
import models.user.login.LoginBodyModel;
import models.user.registration.RegistrationBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static test_data.TestData.*;

@Story("Добавление комментария API")
public class AddReviewTests extends TestBase {

    @Test
    @DisplayName("Добавление комментария создателем клуба")
    public void addOwnerCLubReviewTest () {
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

    @Test
    @DisplayName("Добавление комментария клубу под гостем")
    public void addGuestClubReviewTest () {
        String accessOwnerToken = api.login.loginUserAndGetAccessToken(
                new LoginBodyModel(USERNAME, PASSWORD)
        );

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                faker.fakerPublicationYear,
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );
        SuccessfulCreateClubResponseModel responseClubData = api.createClub.createClub(clubData, accessOwnerToken);

        AddReviewBodyModel reviewData = new AddReviewBodyModel(
                responseClubData.id(),
                faker.fakerDescription,
                faker.fakerAssessment,
                faker.fakerReadPages
        );

        SuccessfulRegistrationAndGetResponseModel guestDataUser = api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword)
        );

        String accessGuestToken = api.login.loginUserAndGetAccessToken(
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword)
        );

        SuccessfulAddReviewsResponseModel addReviewsResponse =  api.addReviews.addReview(reviewData, accessGuestToken);

        verificationSteps.moreThan("id", addReviewsResponse.id(), 0);
        verificationSteps.shouldHaveIntValue("club", addReviewsResponse.club(), responseClubData.id());
        verificationSteps.shouldHaveIntValue("user id", addReviewsResponse.user().id(), guestDataUser.id());
        verificationSteps.shouldHaveValue("username", addReviewsResponse.user().username(), faker.fakerUserFirstName);
        verificationSteps.shouldHaveValue("review", addReviewsResponse.review(), faker.fakerDescription);
        verificationSteps.shouldHaveIntValue("assessment", addReviewsResponse.assessment(), faker.fakerAssessment);
        verificationSteps.shouldHaveIntValue("readPages", addReviewsResponse.readPages(), faker.fakerReadPages);
        verificationSteps.isNotEmpty("created", addReviewsResponse.created());
        verificationSteps.isNull("modified", addReviewsResponse.modified());
    }

    @Test
    @DisplayName("Передача не валидного body комментария")
    public void addInvalideReviewTest () {
        String accessToken = api.login.loginUserAndGetAccessToken(
                new LoginBodyModel(USERNAME, PASSWORD)
        );

        AddReviewBodyModel reviewData = new AddReviewBodyModel(
                0,
                "",
                0,
                0
        );

        WrongReviewDataResponseModel addWrongReviewsResponse = api.addReviews.addWrongReview(reviewData, accessToken);

        verificationSteps.shouldHaveValue("club", addWrongReviewsResponse.club().getFirst(), INVALIDE_ID_CLUB_MESSAGE);
        verificationSteps.shouldHaveValue("review", addWrongReviewsResponse.review().getFirst(), EMPTY_FIELD_REVIEWS_MESSAGE);
        verificationSteps.shouldHaveValue("assessment", addWrongReviewsResponse.assessment().getFirst(), INVALIDE_ASSESSMENT_MESSAGE);
    }
}
