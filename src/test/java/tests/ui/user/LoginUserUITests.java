package tests.ui.user;

import base.TestBase;
import models.user.registration.RegistrationBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginUserUITests extends TestBase {

    @Test
    @DisplayName("Успешная авторизация новым пользователем")
    public void loginNewUserUITest(){

        RegistrationBodyModel registrationData = new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword);
        api.registre.registerUser(registrationData);

        mainPage.openMainPage();
        mainPage.openLoginPage();
        loginPage.fillUsername(faker.fakerUserFirstName);
        loginPage.fillPass(faker.fakerPassword);
        loginPage.clickSubmitButton();
        mainPage.checkButtonLoginUser();
    }

    @Test
    @DisplayName("Успешная авторизация существующим пользователем")
    public void loginOldUserUITest(){
        String username = "Tim42";
        String password = "qweasd";

        loginPage.openThisPage();
        loginPage.fillUsername(username);
        loginPage.fillPass(password);
        loginPage.clickSubmitButton();
        mainPage.checkButtonLoginUser();
    }
}
