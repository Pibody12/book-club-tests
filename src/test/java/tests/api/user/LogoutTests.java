package tests.api.user;

import base.TestBase;
import io.qameta.allure.Story;
import models.user.login.LoginBodyModel;
import models.user.logout.EmptyBodyLogoutResponseModel;
import models.user.logout.ListWithTwoTokenLogoutResponseModel;
import models.user.logout.WrongRefreshTokenLogoutResponseModel;
import models.user.registration.RegistrationBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static test_data.TestData.*;

@Story("Logout API")
public class LogoutTests  extends TestBase {

    @Test
    @DisplayName("Успешный логаут")
    public void successfulLogoutTest() {

        RegistrationBodyModel registrationData =
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword);
        api.registre.registerUser(registrationData);

        LoginBodyModel loginData =
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword);
        String refreshToken = api.login.loginUserAndGetRefreshToken(loginData);


        String logoutData = format("{\"refresh\":\"%s\"}", refreshToken);
        api.logout.logoutUser(logoutData);
    }

    @Test
    @DisplayName("Ошибка в токене логаут")
    public void invalideRefreshTokenLogoutTest() {

        WrongRefreshTokenLogoutResponseModel logoutResponse = api.logout.wrongRefreshTokenLogout(WRONG_REFRESH_TOKEN);

        verificationSteps.shouldHaveValue("detail", logoutResponse.detail(), INVALIDE_REFRESH_TOKEN_MESSAGE);
    }

    @Test
    @DisplayName("Пустое тело запроса логаут")
    public void emptyBodyResponseLogoutTest() {

        EmptyBodyLogoutResponseModel logoutResponse = api.logout.emptyBodyLogout("");

        verificationSteps.shouldHaveValue("Refresh", logoutResponse.refresh().getFirst(), REQUIRED_FIELD_MESSAGE);
    }

    @Test
    @DisplayName("Не правильный тип данных в запросе логаут и проверка ответа")
    public void listWithTwoTokenResponseLogoutTest() {

        String[] massiveToken = {"token1", "token2"};
        ListWithTwoTokenLogoutResponseModel logoutResponse = api.logout.otherTypeDataLogout(massiveToken);

        verificationSteps.shouldHaveValue("nonFieldErrors", logoutResponse.nonFieldErrors().getFirst(), INVALIDE_DATA_MESSAGE);
    }
}