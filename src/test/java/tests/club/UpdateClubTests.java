package tests.club;

import base.TestBase;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.club.update.SuccessfulUpdateClubResponseModel;
import models.club.update.UpdateClubBodyModel;
import models.user.login.LoginBodyModel;
import models.user.registration.RegistrationBodyModel;
import models.user.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;

public class UpdateClubTests extends TestBase {

    @Test
    @DisplayName("Изменение клуба")
    public void updateClub() {
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
        SuccessfulCreateClubResponseModel responseCreateData = api.createClub.createClub(clubData, token);

        UpdateClubBodyModel updateData = new UpdateClubBodyModel(
                fakerTestData.fakerBookTitle,
                fakerTestData.fakerbookAuthors,
                fakerTestData.fakerPublicationYear,
                fakerTestData.fakerDescription,
                fakerTestData.fakerTelegramChatLink
        );

        SuccessfulUpdateClubResponseModel respUpdateData = api.updateClub.updateClub(updateData, token, responseCreateData.id());

        verificationSteps.moreThan("id", respUpdateData.id(), 0);
        verificationSteps.shouldHaveValue("bookTitle", respUpdateData.bookTitle(), fakerTestData.fakerBookTitle);
        verificationSteps.shouldHaveValue("bookAuthors", respUpdateData.bookAuthors(), fakerTestData.fakerbookAuthors);
        verificationSteps.shouldHaveIntValue("year", respUpdateData.publicationYear(), fakerTestData.fakerPublicationYear);
        verificationSteps.shouldHaveValue("description", respUpdateData.description(), fakerTestData.fakerDescription);
        verificationSteps.shouldHaveValue("telegramChatLink", respUpdateData.telegramChatLink(), fakerTestData.fakerTelegramChatLink);
        verificationSteps.shouldHaveIntValue("Owner", respUpdateData.owner(), registryResponse.id());
        verificationSteps.shouldHaveValue("members", respUpdateData.members().get(0), "" + registryResponse.id() + "");
        verificationSteps.isNotEmpty("created", respUpdateData.created());
        verificationSteps.isNotEmpty("modified", respUpdateData.modified());
    }
}
