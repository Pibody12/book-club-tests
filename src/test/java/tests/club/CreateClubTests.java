package tests.club;

import base.TestBaseClub;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.user.login.LoginBodyModel;
import models.user.registration.RegistrationBodyModel;
import models.user.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_data.FakerTestData;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;


public class CreateClubTests extends TestBaseClub {

    FakerTestData fakerTestData = new FakerTestData();

    @Test
    @DisplayName("Создание клуба")
    public void createClubTest() {
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
        SuccessfulCreateClubResponseModel responseData = api.createClub.createClub(clubData, token);

        assertThat(responseData.id()).isGreaterThan(0);
        assertThat(responseData.bookTitle()).isEqualTo(fakerTestData.fakerBookTitle);
        assertThat(responseData.bookAuthors()).isEqualTo(fakerTestData.fakerbookAuthors);
        assertThat(responseData.publicationYear()).isEqualTo(fakerTestData.fakerPublicationYear);
        assertThat(responseData.description()).isEqualTo(fakerTestData.fakerDescription);
        assertThat(responseData.telegramChatLink()).isEqualTo(fakerTestData.fakerTelegramChatLink);
        assertThat(responseData.owner()).isEqualTo(registryResponse.id());
        assertThat(responseData.members().get(0)).isEqualTo("" + registryResponse.id() + "");
        assertThat(responseData.created()).isNotEmpty();
        assertThat(responseData.modified()).isNull();
    }
}
