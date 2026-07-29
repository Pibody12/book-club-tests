package tests.ui.club;

import base.TestBase;
import models.localstorage.LocalStorageAuthModel;
import models.user.login.LoginBodyModel;
import models.user.login.SuccessfulLoginResponseModel;
import models.user.registration.RegistrationBodyModel;
import models.user.SuccessfulRegistrationAndGetResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CreateClubUITests extends TestBase {

    @Test
    @DisplayName("Успешное создание клуба")
    public void successfulCreateClub(){
        // register user
        SuccessfulRegistrationAndGetResponseModel registrationResponse = api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword));
        // login user
        SuccessfulLoginResponseModel loginResponse = api.login.loginUser(
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword));

        String accessToken = loginResponse.access();
        String refreshToken = loginResponse.refresh();
        String localstorageAuthBody = new LocalStorageAuthModel(registrationResponse, accessToken, refreshToken, true).toJson();

        generalSteps.authInBrowser(localstorageAuthBody);
        createClubPage.openThisPage();
        createClubPage.fillForm(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                String.valueOf(faker.fakerPublicationYear),
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );
        createClubPage.clickSubmitButton();
        mainPage.findClub(faker.fakerBookTitle);
        mainPage.checkClubCards(
                faker.fakerBookTitle,
                String.valueOf(faker.fakerPublicationYear),
                faker.fakerbookAuthors,
                faker.fakerDescription);
    }
}
