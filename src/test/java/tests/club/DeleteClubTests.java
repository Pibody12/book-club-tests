package tests.club;

import base.TestBaseClub;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.club.get.GetDeletedClubResponseModel;
import models.user.login.LoginBodyModel;
import models.user.registration.RegistrationBodyModel;
import models.user.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_data.FakerTestData;

import static java.lang.String.format;

public class DeleteClubTests extends TestBaseClub {

    FakerTestData fakerTestData = new FakerTestData();

    @Test
    @DisplayName("Удаление клуба")
    public void deleteClubTest() {
        RegistrationBodyModel registrationData =
                new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);
        SuccessfulRegistrationResponseModel registryResponse = api.registre.registerUser(registrationData);

        LoginBodyModel loginData =
                new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);
        String accessToken = api.login.loginUserAndGetAccessToken(loginData);
        String token = format("Bearer %s", accessToken);

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                fakerTestData.fakerBookTitle,
                fakerTestData.fakerbookAuthors,
                fakerTestData.fakerPublicationYear,
                fakerTestData.fakerDescription,
                fakerTestData.fakerTelegramChatLink
        );
        SuccessfulCreateClubResponseModel createData = api.createClub.createClub(clubData, token);

        Integer id = createData.id();
        api.deleteClub.deleteClub(token, id);

        GetDeletedClubResponseModel getResponse = api.getClub.getDeletedClub(token, id);

        String expectedDetailError = "No Club matches the given query.";
        String actualDetailError = getResponse.detail();
        Assertions.assertEquals(expectedDetailError, actualDetailError);
    }
}
