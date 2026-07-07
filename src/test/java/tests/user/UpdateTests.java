package tests.user;

import base.TestBase;
import models.user.login.LoginBodyModel;
import models.user.registration.RegistrationBodyModel;
import models.user.update.SuccessfulUpdatePatchResponseModel;
import models.user.update.SuccessfulUpdatePutResponseModel;
import models.user.update.UpdatePatchBodyModel;
import models.user.update.UpdatePutBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test_data.FakerTestData;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class UpdateTests extends TestBase {

    FakerTestData fakerTestData = new FakerTestData();

    @Test
    @DisplayName("Обновление всех данных пользователя")
    public void successfulPutUpdateTest(){

        RegistrationBodyModel registrationData =
                new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);
        api.registre.registerUser(registrationData);

        LoginBodyModel loginData =
                new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);
        String accessToken = api.login.loginUserAndGetAccessToken(loginData);

        String token = format("Bearer %s", accessToken);
        UpdatePutBodyModel updateData = new UpdatePutBodyModel(
                fakerTestData.fakerUserFirstName, "Tim", "Drob", "test@test.com");
        SuccessfulUpdatePutResponseModel updateResponse = api.update.updatePutUser(updateData, token);

        assertThat(updateResponse.id()).isGreaterThan(0);
        assertThat(updateResponse.username()).isEqualTo(fakerTestData.fakerUserFirstName);
        assertThat(updateResponse.firstName()).isEqualTo("Tim");
        assertThat(updateResponse.lastName()).isEqualTo("Drob");
        assertThat(updateResponse.email()).isEqualTo("test@test.com");
    }

    @Test
    @DisplayName("Обновление части данных пользователя")
    public void successfulPatchUpdateTest(){

        RegistrationBodyModel registrationData =
                new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);
        api.registre.registerUser(registrationData);

        LoginBodyModel loginData =
                new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);
        String accessToken =  api.login.loginUserAndGetAccessToken(loginData);

        String token = format("Bearer %s", accessToken);
        UpdatePatchBodyModel updateData =
                new UpdatePatchBodyModel("Tim42", "Drob42");
        SuccessfulUpdatePatchResponseModel updateResponse = api.update.updatePatchUser(updateData, token);

        assertThat(updateResponse.id()).isGreaterThan(0);
        assertThat(updateResponse.username()).isEqualTo(fakerTestData.fakerUserFirstName);
        assertThat(updateResponse.firstName()).isEqualTo("Tim42");
        assertThat(updateResponse.lastName()).isEqualTo("Drob42");
    }
}
