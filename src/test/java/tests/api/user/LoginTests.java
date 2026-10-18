package tests.api.user;


import base.TestBase;
import models.user.login.*;
import models.user.registration.RegistrationBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static test_data.TestData.*;

public class LoginTests extends TestBase {

    String wrongPassword = "string666";

    @Test
    @DisplayName("Успешная авторизация")
    public void successfulLoginTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword);
        api.registre.registerUser(registrationData);

        LoginBodyModel loginData = new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword);
        SuccessfulLoginResponseModel loginResponse = api.login.loginUser(loginData);

        String actualAccess = loginResponse.access();
        String actualRefresh = loginResponse.refresh();

        verificationSteps.shouldHaveBegin("Access token", actualAccess, PREFIX_TOKEN);
        verificationSteps.shouldHaveBegin("Refresh token", actualRefresh, PREFIX_TOKEN);
        verificationSteps.notShouldHaveValue("Access token", "Refresh token", actualAccess, actualRefresh);
    }

    @Test
    @DisplayName("Ошибка в пароле при авторизации")
    public void wrongCredentialsLoginTest(){

        api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserTwoFirstName, faker.fakerPassword)
        );

        WrongCredetialsLoginResponseModel loginResponse = api.login.wrongPasswordLogin(
                new LoginBodyModel(faker.fakerUserFirstName, wrongPassword)
        );

        verificationSteps.shouldHaveBegin("Detail", loginResponse.detail(), INVALIDE_CREDLS);
    }

    @Test
    @DisplayName("Авторизация с пустыми данными")
    public void emptyCredentialsLoginTest(){

        EmptyCredentialsResponseModel loginResponse = api.login.emptyPasswordLogin(
                new LoginBodyModel("", "")
        );

        verificationSteps.shouldHaveValue("Username", loginResponse.username().getFirst(), BLANK_FIELD);
        verificationSteps.shouldHaveValue("Password", loginResponse.password().getFirst(), BLANK_FIELD);
    }

    @Test
    @DisplayName("Авторизация с пустым телом в запросе")
    public void emptyBodyLoginTest(){

        EmptyBodyResponseModel loginResponse = api.login.emptyBodyLogin(
                new LoginEmptyBodyModel()
        );

        verificationSteps.shouldHaveValue("Username", loginResponse.username().getFirst(), REQUIRED_FIELD_MESSAGE);
        verificationSteps.shouldHaveValue("Password", loginResponse.password().getFirst(), REQUIRED_FIELD_MESSAGE);
    }
}
