package tests.club;

import base.TestBase;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.user.login.LoginBodyModel;
import models.user.registration.RegistrationBodyModel;
import models.user.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;


public class CreateClubTests extends TestBase {

    @Test
    @DisplayName("Создание клуба")
    public void createClubTest() {
        SuccessfulRegistrationResponseModel registryResponse = api.registre.registerUser(
                new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword)
        );

        String accessToken = api.login.loginUserAndGetAccessToken(
                new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword)
        );
        String token = format("Bearer %s", accessToken);

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                fakerTestData.fakerBookTitle,
                fakerTestData.fakerbookAuthors,
                fakerTestData.fakerPublicationYear,
                fakerTestData.fakerDescription,
                fakerTestData.fakerTelegramChatLink
        );
        SuccessfulCreateClubResponseModel responseData = api.createClub.createClub(clubData, token);


        verificationSteps.moreThan("id", responseData.id(), 0);
        verificationSteps.shouldHaveValue("bookTitle", responseData.bookTitle(), fakerTestData.fakerBookTitle);
        verificationSteps.shouldHaveValue("bookAuthors", responseData.bookAuthors(), fakerTestData.fakerbookAuthors);
        verificationSteps.shouldHaveIntValue("publicationYear", responseData.publicationYear(), fakerTestData.fakerPublicationYear);
        verificationSteps.shouldHaveValue("description", responseData.description(), fakerTestData.fakerDescription);
        verificationSteps.shouldHaveValue("telegramChatLink", responseData.telegramChatLink(), fakerTestData.fakerTelegramChatLink);
        verificationSteps.shouldHaveIntValue("Owner", responseData.owner(), registryResponse.id());
        verificationSteps.shouldHaveValue("members", responseData.members().get(0), "" + registryResponse.id() + "");
        verificationSteps.isNotEmpty("created", responseData.created());
        verificationSteps.isNull("modified", responseData.modified());
    }
}
