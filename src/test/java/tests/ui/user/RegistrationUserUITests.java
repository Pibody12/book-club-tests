package tests.ui.user;

import base.TestBase;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Story("Регистрация UI")
public class RegistrationUserUITests extends TestBase {

    @Test
    @DisplayName("Проверяем успешную регистрацию")
    public void successfulRegistrationUserUITest (){
        mainPage.openMainPage();
        mainPage.openRegistrationPage();
        regPage.fillUsername(faker.fakerUserFirstName);
        regPage.fillPass(faker.fakerPassword);
        regPage.clickSignupButton();
        mainPage.checkButtonLoginUser();
    }

    @Test
    @DisplayName("Регистрация с неверным подтверждение пароля")
    public void wrongConfirmPasswordRegistrationUserUITest (){
        mainPage.openMainPage();
        mainPage.openRegistrationPage();
        regPage.fillUsername(faker.fakerUserFirstName);
        regPage.fillPassWithWrongConfirm(faker.fakerPassword);
        regPage.clickSignupButton();
        regPage.checkErrorMessage();
    }
}
