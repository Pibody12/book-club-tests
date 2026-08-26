package tests.ui.review;

import base.TestBase;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.localstorage.LocalStorageAuthModel;
import models.user.SuccessfulRegistrationAndGetResponseModel;
import models.user.login.LoginBodyModel;
import models.user.login.SuccessfulLoginResponseModel;
import models.user.registration.RegistrationBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.refresh;

public class CreateReviewUITests extends TestBase {

    @Test
    @DisplayName("Успешное создание комментария на собственный клуб")
    public  void successfulOwnerCreateReviewUITest() {

        SuccessfulRegistrationAndGetResponseModel registrationResponse = api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword));

        SuccessfulLoginResponseModel loginResponse = api.login.loginUser(
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword));

        String accessToken = loginResponse.access();
        String refreshToken = loginResponse.refresh();

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                faker.fakerPublicationYear,
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );
        SuccessfulCreateClubResponseModel responseData = api.createClub.createClub(clubData, accessToken);

        String localstorageAuthBody = new LocalStorageAuthModel(registrationResponse, accessToken, refreshToken, true).toJson();

        generalSteps.authInBrowser(localstorageAuthBody);
        clubsPage.openClubPage(responseData.id());
        clubsPage.openReviewForm();
        clubsPage.fillAssessment(faker.fakerAssessment);
        clubsPage.fillReadPages(faker.fakerReadPages);
        clubsPage.fillReview(faker.fakerDescription);
        clubsPage.publishReview();
        refresh();
        clubsPage.checkAmountReviews("1");
        clubsPage.checkReviewCards(faker.fakerUserFirstName, faker.fakerAssessment, faker.fakerReadPages, faker.fakerDescription);
    }

    @Test
    @DisplayName("Успешное создание комментария на собственный клуб")
    public  void successfulGuestCreateReviewUITest() {
        String guestUsername = "Tim42";
        String guestPassword = "qweasd";

        SuccessfulRegistrationAndGetResponseModel registrationResponse = api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword));

        SuccessfulLoginResponseModel loginResponse = api.login.loginUser(
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword));

        String accessToken = loginResponse.access();

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                faker.fakerPublicationYear,
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );
        SuccessfulCreateClubResponseModel responseData = api.createClub.createClub(clubData, accessToken);

        SuccessfulLoginResponseModel loginGuestResponse = api.login.loginUser(
                new LoginBodyModel(guestUsername, guestPassword));

        api.joinClub.joinClub(loginGuestResponse.access(), responseData.id());

        SuccessfulRegistrationAndGetResponseModel getUserResponse = api.getUser.getUser(loginGuestResponse.access());

        String localstorageAuthBody = new LocalStorageAuthModel(getUserResponse, loginGuestResponse.access(), loginGuestResponse.refresh(), true).toJson();

        generalSteps.authInBrowser(localstorageAuthBody);
        clubsPage.openClubPage(responseData.id());
        clubsPage.openReviewForm();
        clubsPage.fillAssessment(faker.fakerAssessment);
        clubsPage.fillReadPages(faker.fakerReadPages);
        clubsPage.fillReview(faker.fakerDescription);
        clubsPage.publishReview();
        refresh();
        clubsPage.checkAmountReviews("1");
        clubsPage.checkReviewCards(guestUsername, faker.fakerAssessment, faker.fakerReadPages, faker.fakerDescription);
    }
}
