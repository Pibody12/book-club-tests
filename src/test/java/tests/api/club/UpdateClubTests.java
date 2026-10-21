package tests.api.club;

import base.TestBase;
import io.qameta.allure.Story;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.club.update.SuccessfulUpdateClubResponseModel;
import models.club.update.UpdateClubBodyModel;
import models.user.SuccessfulRegistrationAndGetResponseModel;
import models.user.login.LoginBodyModel;
import models.user.registration.RegistrationBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Story("Иминение клуба API")
public class UpdateClubTests extends TestBase {

    @Test
    @DisplayName("Изменение клуба")
    public void updateClub() {
        SuccessfulRegistrationAndGetResponseModel registryResponse = api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword)
        );

        String accessToken = api.login.loginUserAndGetAccessToken(
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword)
        );
//        String token = format("Bearer %s", accessToken);

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                faker.fakerPublicationYear,
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );
        SuccessfulCreateClubResponseModel responseCreateData = api.createClub.createClub(clubData, accessToken);

        UpdateClubBodyModel updateData = new UpdateClubBodyModel(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                faker.fakerPublicationYear,
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );

        SuccessfulUpdateClubResponseModel respUpdateData = api.updateClub.updateClub(updateData, accessToken, responseCreateData.id());

        verificationSteps.moreThan("id", respUpdateData.id(), 0);
        verificationSteps.shouldHaveValue("bookTitle", respUpdateData.bookTitle(), faker.fakerBookTitle);
        verificationSteps.shouldHaveValue("bookAuthors", respUpdateData.bookAuthors(), faker.fakerbookAuthors);
        verificationSteps.shouldHaveIntValue("year", respUpdateData.publicationYear(), faker.fakerPublicationYear);
        verificationSteps.shouldHaveValue("description", respUpdateData.description(), faker.fakerDescription);
        verificationSteps.shouldHaveValue("telegramChatLink", respUpdateData.telegramChatLink(), faker.fakerTelegramChatLink);
        verificationSteps.shouldHaveIntValue("Owner", respUpdateData.owner(), registryResponse.id());
        verificationSteps.shouldHaveValue("members", respUpdateData.members().get(0), "" + registryResponse.id() + "");
        verificationSteps.isNotEmpty("created", respUpdateData.created());
        verificationSteps.isNotEmpty("modified", respUpdateData.modified());
    }
}
