package tests.ui.review;

import base.TestBase;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.localstorage.LocalStorageAuthModel;
import models.reviews.add.AddReviewBodyModel;
import models.user.SuccessfulRegistrationAndGetResponseModel;
import models.user.login.LoginBodyModel;
import models.user.login.SuccessfulLoginResponseModel;
import models.user.registration.RegistrationBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.confirm;
import static test_data.TestData.PASSWORD;
import static test_data.TestData.USERNAME;

public class UpdateReviewUITests extends TestBase {

    @Test
    @DisplayName("Удаление комментария")
    public void updateReviewUITest () {

        api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword)
        );

        String accessOwnerToken = api.login.loginUserAndGetAccessToken(
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword)
        );

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                faker.fakerPublicationYear,
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );

        SuccessfulCreateClubResponseModel responseClubData = api.createClub.createClub(
                clubData, accessOwnerToken
        );

        AddReviewBodyModel reviewData = new AddReviewBodyModel(
                responseClubData.id(),
                faker.fakerDescription,
                faker.fakerAssessment,
                faker.fakerReadPages
        );

        SuccessfulLoginResponseModel guestLoginResponse = api.login.loginUser(
                new LoginBodyModel(USERNAME, PASSWORD)
        );

        String guestAccessToken = guestLoginResponse.access();
        String guestRefreshToken = guestLoginResponse.refresh();

        SuccessfulRegistrationAndGetResponseModel guestGetResponse = api.getUser.getUser(
                guestAccessToken
        );

        api.addReviews.addReview(reviewData, guestAccessToken);

        String localstorageAuthBody = new LocalStorageAuthModel(guestGetResponse, guestAccessToken, guestRefreshToken, true).toJson();

        generalSteps.authInBrowser(localstorageAuthBody);
        clubsPage.openClubPage( responseClubData.id());
        clubsPage.editReview();
        clubsPage.fillReadPages(faker.fakerReadPages + 10);
        clubsPage.fillReview("Update review test");
        clubsPage.publishReview();
        clubsPage.checkReviewCards(USERNAME, faker.fakerAssessment, faker.fakerReadPages + 10, "Update review test");
    }
}
