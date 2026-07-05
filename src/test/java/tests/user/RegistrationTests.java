package tests.user;

import base.TestBaseUser;
import models.user.registration.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationTests extends TestBaseUser {

    @Test
    @DisplayName("Успешная регистрация")
    public void successfulRegistrationTest(){

        RegistrationBodyModel registrationData =
                new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);
        SuccessfulRegistrationResponseModel registrationResponse = api.registre.registerUser(registrationData);

        assertThat(registrationResponse.id()).isGreaterThan(0);
        assertThat(registrationResponse.username()).isEqualTo(fakerTestData.fakerUserFirstName);
        assertThat(registrationResponse.firstName()).isEqualTo("");
        assertThat(registrationResponse.lastName()).isEqualTo("");
        assertThat(registrationResponse.email()).isEqualTo("");

        String ipAddrRegexp = "^((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)\\.){3}"
                + "(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)$";
        assertThat(registrationResponse.remoteAddr()).matches(ipAddrRegexp);
    }

    @Test
    @DisplayName("Повторная регистрация с существующими данными")
    public void existingUserWrongRegistrationTest(){

        RegistrationBodyModel registrationData =
                new RegistrationBodyModel(fakerTestData.fakerUserFirstName, fakerTestData.fakerPassword);

        SuccessfulRegistrationResponseModel firstRegistrationResponse = api.registre.registerUser(registrationData);
        assertThat(firstRegistrationResponse.username()).isEqualTo(fakerTestData.fakerUserFirstName);

        ExistingUserResponseModel secondRegistrationResponse = api.registre.registerExistingUser(registrationData);

        String expectedError = "A user with that username already exists.";
        String actualError = secondRegistrationResponse.username().getFirst();
        assertThat(actualError).isEqualTo(expectedError);
    }

    @Test
    @DisplayName("Регистрация с пустыми данными")
    public void emptyRegistrationsDataRegistrationTest(){

        RegistrationBodyModel registrationData =
                new RegistrationBodyModel("", "");
        EmptyRegistrationsDataResponseModel registrationResponse = api.registre.registerEmptyData(registrationData);

        String expectedError = "This field may not be blank.";
        String actualUsernameError = registrationResponse.username().getFirst();
        String actualPasswordError = registrationResponse.username().getLast();
        assertThat(actualUsernameError).isEqualTo(expectedError);
        assertThat(actualPasswordError).isEqualTo(expectedError);
    }

    @Test
    @DisplayName("Регистрация с невалидным именем")
    public void invalideUsernameRegistrationTest(){

        RegistrationBodyModel registrationData =
                new RegistrationBodyModel(fakerTestData.fakerInvalideName, fakerTestData.fakerPassword);
        InvalideNameResponseModel registrationResponse = api.registre.registerInvalideData(registrationData);

        String expectedError = "Enter a valid username. This value may contain only letters, numbers, and @/./+/-/_ characters.";
        String actualError = registrationResponse.username().getFirst();
        assertThat(actualError).isEqualTo(expectedError);
    }
}
