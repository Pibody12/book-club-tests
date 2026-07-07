package tests.user;


import base.TestBase;
import models.user.login.*;
import models.user.registration.RegistrationBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests extends TestBase {

    String wrongPassword = "string666";

    @Test
    @DisplayName("Успешная авторизация")
    public void successfulLoginTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);
        api.registre.registerUser(registrationData);

        LoginBodyModel loginData = new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);
        SuccessfulLoginResponseModel loginResponse = api.login.loginUser(loginData);

        String expectedTokenPath = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
        String actualAccess = loginResponse.access();
        String actualRefresh = loginResponse.refresh();

        assertThat(actualAccess).startsWith(expectedTokenPath);
        assertThat(actualRefresh).startsWith(expectedTokenPath);
        assertThat(actualAccess).isNotEqualTo(actualRefresh);
    }

    @Test
    @DisplayName("Ошибка в пароле при авторизации")
    public void wrongCredentialsLoginTest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);
        api.registre.registerUser(registrationData);

        LoginBodyModel loginData = new LoginBodyModel(fakerTestData.fakerUserFirstName, wrongPassword);
        WrongCredetialsLoginResponseModel loginResponse = api.login.wrongPasswordLogin(loginData);

        String expectedDetailError = "Invalid username or password.";
        String actualDetailError = loginResponse.detail();
        assertThat(actualDetailError).isEqualTo(expectedDetailError);
    }

    @Test
    @DisplayName("Авторизация с пустыми данными")
    public void emptyCredentialsLoginTest(){

        LoginBodyModel loginEmptyData = new LoginBodyModel("", "");
        EmptyCredentialsResponseModel loginResponse = api.login.emptyPasswordLogin(loginEmptyData);

        String expectedError = "This field may not be blank.";
        String actualUsernameError = loginResponse.username().getFirst();
        String actualPasswordError = loginResponse.password().getFirst();
        assertThat(actualUsernameError).isEqualTo(expectedError);
        assertThat(actualPasswordError).isEqualTo(expectedError);
    }

    @Test
    @DisplayName("Авторизация с пустым телом в запросе")
    public void emptyBodyLoginTest(){

        LoginEmptyBodyModel loginData = new LoginEmptyBodyModel();
        EmptyBodyResponseModel loginResponse = api.login.emptyBodyLogin(loginData);

        String expectedError = "This field is required.";
        String actualUsernameError = loginResponse.username().getFirst();
        String actualPasswordError = loginResponse.password().getFirst();
        assertThat(actualUsernameError).isEqualTo(expectedError);
        assertThat(actualPasswordError).isEqualTo(expectedError);
    }
}
