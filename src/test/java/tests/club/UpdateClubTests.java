package tests.club;

import base.TestBaseClub;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.club.create.SuccessfulUpdateClubResponseModel;
import models.club.update.UpdateClubBodyModel;
import models.user.login.LoginBodyModel;
import models.user.registration.RegistrationBodyModel;
import models.user.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_data.FakerTestData;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateClubTests extends TestBaseClub {

    FakerTestData fakerTestData = new FakerTestData();

    @Test
    @DisplayName("Изменение клуба")
    public void updateClub() {
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
        SuccessfulCreateClubResponseModel responseCreateData = api.createClub.createClub(clubData, token);
        Integer id = responseCreateData.id();

        UpdateClubBodyModel updateData = new UpdateClubBodyModel(
                fakerTestData.fakerBookTitle,
                fakerTestData.fakerbookAuthors,
                fakerTestData.fakerPublicationYear,
                fakerTestData.fakerDescription,
                fakerTestData.fakerTelegramChatLink
        );

        SuccessfulUpdateClubResponseModel successfulUpdateData = api.updateClub.updateClub(updateData, token, id);

        assertThat(successfulUpdateData.id()).isGreaterThan(0);
        assertThat(successfulUpdateData.bookTitle()).isEqualTo(fakerTestData.fakerBookTitle);
        assertThat(successfulUpdateData.bookAuthors()).isEqualTo(fakerTestData.fakerbookAuthors);
        assertThat(successfulUpdateData.publicationYear()).isEqualTo(fakerTestData.fakerPublicationYear);
        assertThat(successfulUpdateData.description()).isEqualTo(fakerTestData.fakerDescription);
        assertThat(successfulUpdateData.telegramChatLink()).isEqualTo(fakerTestData.fakerTelegramChatLink);
        assertThat(successfulUpdateData.owner()).isEqualTo(registryResponse.id());
        assertThat(successfulUpdateData.members().get(0)).isEqualTo("" + registryResponse.id() + "");
        assertThat(successfulUpdateData.created()).isNotEmpty();
        assertThat(successfulUpdateData.modified()).isNotEmpty();
    }
}
