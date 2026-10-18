package tests.api.user;

import base.TestBase;
import models.user.login.LoginBodyModel;
import models.user.registration.RegistrationBodyModel;
import models.user.update.SuccessfulUpdatePatchResponseModel;
import models.user.update.SuccessfulUpdatePutResponseModel;
import models.user.update.UpdatePatchBodyModel;
import models.user.update.UpdatePutBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;

public class UpdateTests extends TestBase {

    @Test
    @DisplayName("Обновление всех данных пользователя")
    public void successfulPutUpdateTest(){

        api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword)
        );

        String accessToken = api.login.loginUserAndGetAccessToken(
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword)
        );

        String token = format("Bearer %s", accessToken);
        String firstName = "Tim";
        String lastName = "Drob";
        String email = "test@test.com";

        UpdatePutBodyModel updateData = new UpdatePutBodyModel(
                faker.fakerUserFirstName, firstName, lastName, email);

        SuccessfulUpdatePutResponseModel updateResponse =
                api.update.updatePutUser(updateData, token);

        verificationSteps.moreThan("id", updateResponse.id(), 0);
        verificationSteps.shouldHaveValue("username", updateResponse.username(), faker.fakerUserFirstName);
        verificationSteps.shouldHaveValue("first name", updateResponse.firstName(), firstName);
        verificationSteps.shouldHaveValue("last name", updateResponse.lastName(), lastName);
        verificationSteps.shouldHaveValue("email", updateResponse.email(), email);
    }

    @Test
    @DisplayName("Обновление части данных пользователя")
    public void successfulPatchUpdateTest(){

        api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserTwoFirstName, faker.fakerPassword)
        );

        String accessToken =  api.login.loginUserAndGetAccessToken(
                new LoginBodyModel(faker.fakerUserTwoFirstName, faker.fakerPassword)
        );

        String token = format("Bearer %s", accessToken);
        String firstName = "Tim42";
        String lastName = "Drob42";
        UpdatePatchBodyModel updateData =
                new UpdatePatchBodyModel(firstName, lastName);
        SuccessfulUpdatePatchResponseModel updateResponse = api.update.updatePatchUser(updateData, token);

        verificationSteps.moreThan("id", updateResponse.id(), 0);
        verificationSteps.shouldHaveValue("username", updateResponse.username(), faker.fakerUserTwoFirstName);
        verificationSteps.shouldHaveValue("first name", updateResponse.firstName(), firstName);
        verificationSteps.shouldHaveValue("last name", updateResponse.lastName(), lastName);
    }
}
