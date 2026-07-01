package tests;

import base.TestBase;
import models.login.LoginBodyModel;
import models.logout.EmptyBodyLogoutResponseModel;
import models.logout.ListWithTwoTokenLogoutResponseModel;
import models.logout.WrongRefreshTokenLogoutResponseModel;
import models.registration.RegistrationBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

public class LogoutTests  extends TestBase {

    @Test
    @DisplayName("Успешный логаут")
    public void successfulLogoutTest() {

        RegistrationBodyModel registrationData =
                new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);
        api.registre.registerUser(registrationData);

        LoginBodyModel loginData =
                new LoginBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);
        String refreshToken = api.login.loginUserAndGetRefreshToken(loginData);


        String logoutData = format("{\"refresh\":\"%s\"}", refreshToken);
        api.logout.logoutUser(logoutData);
    }

    @Test
    @DisplayName("Ошибка в токене логаут")
    public void invalideRefreshTokenLogoutTest() {

        String wrongRefreshToken = "wrongRefrshToken";
        WrongRefreshTokenLogoutResponseModel logoutResponse = api.logout.wrongRefreshTokenLogout(wrongRefreshToken);

        String expectedError = "JSON parse error - unexpected character: line 1 column 1 (char 0)";
        String actualError = logoutResponse.detail();
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test
    @DisplayName("Пустое тело запроса логаут")
    public void emptyBodyResponseLogoutTest() {

        EmptyBodyLogoutResponseModel logoutResponse = api.logout.emptyBodyLogout("");

        String expectedError = "This field is required.";
        String actualError = logoutResponse.refresh().getFirst();
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test
    @DisplayName("Не правильный тип данных в запросе логаут и проверка ответа")
    public void listWithTwoTokenResponseLogoutTest() {

        String[] massiveToken = {"token1", "token2"};
        ListWithTwoTokenLogoutResponseModel logoutResponse = api.logout.otherTypeDataLogout(massiveToken);

        String expectedError = "Invalid data. Expected a dictionary, but got list.";
        String actualError = logoutResponse.nonFieldErrors().getFirst();
        assertThat(actualError).isEqualTo(expectedError);
    }
}