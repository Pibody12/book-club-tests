package tests.api.club;

import base.TestBase;
import io.qameta.allure.Story;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.user.SuccessfulRegistrationAndGetResponseModel;
import models.user.login.LoginBodyModel;
import models.user.registration.RegistrationBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Story("Создание клуба API")
public class CreateClubTests extends TestBase {

    @Test
    @DisplayName("Создание клуба")
    public void createClubTest() {
        SuccessfulRegistrationAndGetResponseModel registryResponse = api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword)
        );

        String accessToken = api.login.loginUserAndGetAccessToken(
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword)
        );

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                faker.fakerPublicationYear,
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );
        SuccessfulCreateClubResponseModel responseData = api.createClub.createClub(clubData, accessToken);

        verificationSteps.moreThan("id", responseData.id(), 0);
        verificationSteps.shouldHaveValue("bookTitle", responseData.bookTitle(), faker.fakerBookTitle);
        verificationSteps.shouldHaveValue("bookAuthors", responseData.bookAuthors(), faker.fakerbookAuthors);
        verificationSteps.shouldHaveIntValue("publicationYear", responseData.publicationYear(), faker.fakerPublicationYear);
        verificationSteps.shouldHaveValue("description", responseData.description(), faker.fakerDescription);
        verificationSteps.shouldHaveValue("telegramChatLink", responseData.telegramChatLink(), faker.fakerTelegramChatLink);
        verificationSteps.shouldHaveIntValue("Owner", responseData.owner(), registryResponse.id());
        verificationSteps.shouldHaveValue("members", responseData.members().get(0), "" + registryResponse.id() + "");
        verificationSteps.isNotEmpty("created", responseData.created());
        verificationSteps.isNull("modified", responseData.modified());
    }
}
