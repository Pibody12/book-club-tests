package tests.api.club;

import base.TestBase;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.club.get.GetDeletedClubResponseModel;
import models.user.login.LoginBodyModel;
import models.user.registration.RegistrationBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static test_data.TestData.NO_CLUB_MESSAGE;

public class DeleteClubTests extends TestBase {

    @Test
    @DisplayName("Удаление клуба")
    public void deleteClubTest() {
        api.registre.registerUser(
                new RegistrationBodyModel (faker.fakerUserFirstName, faker.fakerPassword)
        );

        String accessToken = api.login.loginUserAndGetAccessToken(
                new LoginBodyModel (faker.fakerUserFirstName, faker.fakerPassword)
        );

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                faker.fakerPublicationYear,
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );
        SuccessfulCreateClubResponseModel createData = api.createClub.createClub(clubData, accessToken);

        api.deleteClub.deleteClub(accessToken, createData.id());

        GetDeletedClubResponseModel getResponse = api.getClub.getDeletedClub(accessToken, createData.id());

        verificationSteps.shouldHaveValue("detail", NO_CLUB_MESSAGE, getResponse.detail());
    }
}
