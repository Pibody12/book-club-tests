package tests.user;

import base.TestBase;
import models.user.registration.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static test_data.TestData.*;

public class RegistrationTests extends TestBase {

    @Test
    @DisplayName("Успешная регистрация")
    public void successfulRegistrationTest(){

        SuccessfulRegistrationResponseModel registrationResponse = api.registre.registerUser(
                new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword)
        );

        verificationSteps.moreThan("id", registrationResponse.id(), 0);
        verificationSteps.shouldHaveValue("username", registrationResponse.username(), fakerTestData.fakerUserFirstName);
        verificationSteps.shouldHaveEmptyValue("firstName", registrationResponse.firstName());
        verificationSteps.shouldHaveEmptyValue("lastName", registrationResponse.lastName());
        verificationSteps.shouldHaveEmptyValue("email", registrationResponse.email());
        verificationSteps.matches("remoteAddr", registrationResponse.remoteAddr(), ipAddrRegexp);

    }

    @Test
    @DisplayName("Повторная регистрация с существующими данными")
    public void existingUserWrongRegistrationTest(){

        RegistrationBodyModel registrationData =
                new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        SuccessfulRegistrationResponseModel firstRegistrationResponse = api.registre.registerUser(registrationData);

        verificationSteps.shouldHaveValue("username", firstRegistrationResponse.username(), fakerTestData.fakerUserFirstName);

        ExistingUserResponseModel secondRegistrationResponse = api.registre.registerExistingUser(registrationData);

        verificationSteps.shouldHaveValue("username", secondRegistrationResponse.username().getFirst(), EXIST_USER_MESSAGE);
    }

    @Test
    @DisplayName("Регистрация с пустыми данными")
    public void emptyRegistrationsDataRegistrationTest(){

        EmptyRegistrationsDataResponseModel registrationResponse = api.registre.registerEmptyData(
                new RegistrationBodyModel("", "")
        );

        verificationSteps.shouldHaveValue("username", registrationResponse.username().getFirst(), BLANK_FIELD);
        verificationSteps.shouldHaveValue("password", registrationResponse.username().getLast(), BLANK_FIELD);
    }

    @Test
    @DisplayName("Регистрация с невалидным именем")
    public void invalideUsernameRegistrationTest(){

        InvalideNameResponseModel registrationResponse = api.registre.registerInvalideData(
                new RegistrationBodyModel(fakerTestData.fakerInvalideName, fakerTestData.fakerPassword)
        );

        verificationSteps.shouldHaveValue("username", registrationResponse.username().getFirst(), INVALIDE_USERNAME);
    }
}
