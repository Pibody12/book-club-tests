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

        api.registre.registerUser(
                new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword)
        );

        String accessToken = api.login.loginUserAndGetAccessToken(
                new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword)
        );

        String token = format("Bearer %s", accessToken);
        String firstName = "Tim";
        String lastName = "Drob";
        String email = "test@test.com";

        UpdatePutBodyModel updateData = new UpdatePutBodyModel(
                fakerTestData.fakerUserFirstName, firstName, lastName, email);

        SuccessfulUpdatePutResponseModel updateResponse =
                api.update.updatePutUser(updateData, token);

        verificationSteps.moreThan("id", updateResponse.id(), 0);
        verificationSteps.shouldHaveValue("username", updateResponse.username(), fakerTestData.fakerUserFirstName);
        verificationSteps.shouldHaveValue("first name", updateResponse.firstName(), firstName);
        verificationSteps.shouldHaveValue("last name", updateResponse.lastName(), lastName);
        verificationSteps.shouldHaveValue("email", updateResponse.email(), email);
    }

    @Test
    @DisplayName("Обновление части данных пользователя")
    public void successfulPatchUpdateTest(){

        api.registre.registerUser(
                new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword)
        );

        String accessToken =  api.login.loginUserAndGetAccessToken(
                new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword)
        );

        String token = format("Bearer %s", accessToken);
        String firstName = "Tim42";
        String lastName = "Drob42";
        UpdatePatchBodyModel updateData =
                new UpdatePatchBodyModel(firstName, lastName);
        SuccessfulUpdatePatchResponseModel updateResponse = api.update.updatePatchUser(updateData, token);

        verificationSteps.moreThan("id", updateResponse.id(), 0);
        verificationSteps.shouldHaveValue("username", updateResponse.username(), fakerTestData.fakerUserFirstName);
        verificationSteps.shouldHaveValue("first name", updateResponse.firstName(), firstName);
        verificationSteps.shouldHaveValue("last name", updateResponse.lastName(), lastName);
    }
}
