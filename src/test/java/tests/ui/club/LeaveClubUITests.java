package tests.ui.club;

import base.TestBase;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.localstorage.LocalStorageAuthModel;
import models.user.login.LoginBodyModel;
import models.user.login.SuccessfulLoginResponseModel;
import models.user.registration.RegistrationBodyModel;
import models.user.SuccessfulRegistrationAndGetResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.confirm;

public class LeaveClubUITests extends TestBase {

    @Test
    @DisplayName("Выйти из собственного клуба")
    public void cantLeaveClubAsOwnerTest(){

        SuccessfulRegistrationAndGetResponseModel registrationResponse = api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword));

        SuccessfulLoginResponseModel loginResponse = api.login.loginUser(
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword));

        String accessToken = loginResponse.access();
        String refreshToken = loginResponse.refresh();

        String localstorageAuthBody = new LocalStorageAuthModel(registrationResponse, accessToken, refreshToken, true).toJson();

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                faker.fakerPublicationYear,
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );

        SuccessfulCreateClubResponseModel responseData = api.createClub.createClub(clubData, accessToken);

        generalSteps.authInBrowser(localstorageAuthBody);
        clubsPage.openClubPage(responseData.id());

        clubsPage.checkClubCards(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                String.valueOf(faker.fakerPublicationYear),
                faker.fakerDescription
        );
        clubsPage.leaveClub();
        confirm();
        clubsPage.checkErrorMessage();
    }

    @Test
    @DisplayName("Выйти из чужого клуба")
    public void leaveClubAsGuestTest(){
        String guestUsername = "Tim42";
        String guestPassword = "qweasd";

        api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword));

        SuccessfulLoginResponseModel loginOwnerResponse = api.login.loginUser(
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword));

        String accessOwnerToken = loginOwnerResponse.access();

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                faker.fakerPublicationYear,
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );

        SuccessfulCreateClubResponseModel responseData = api.createClub.createClub(clubData, accessOwnerToken);

        SuccessfulLoginResponseModel loginGuestResponse = api.login.loginUser(
                new LoginBodyModel(guestUsername, guestPassword));

        api.joinClub.joinClub(loginGuestResponse.access(), responseData.id());

        SuccessfulRegistrationAndGetResponseModel getUserResponse = api.getUser.getUser(loginGuestResponse.access());

        String localstorageAuthBody = new LocalStorageAuthModel(getUserResponse, loginGuestResponse.access(), loginGuestResponse.refresh(), true).toJson();

        generalSteps.authInBrowser(localstorageAuthBody);
        clubsPage.openClubPage(responseData.id());
        clubsPage.checkClubCards(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                String.valueOf(faker.fakerPublicationYear),
                faker.fakerDescription
        );
        clubsPage.leaveClub();
        confirm();
        clubsPage.openClubPage(responseData.id());
        clubsPage.checkHasNotJoinClub();
    }
}
