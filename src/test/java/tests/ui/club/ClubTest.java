package tests.ui.club;

import base.TestBase;
import models.club.create.CreateClubBodyModel;
import models.club.create.SuccessfulCreateClubResponseModel;
import models.localstorage.LocalStorageAuthModel;
import models.user.login.LoginBodyModel;
import models.user.login.SuccessfulLoginResponseModel;
import models.user.registration.RegistrationBodyModel;
import models.user.SuccessfulRegistrationAndGetResponseModel;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ClubTest extends TestBase {

    @Test
    public void cantLeaveClubAsOwnerTest2(){
        // register user
        SuccessfulRegistrationAndGetResponseModel registrationResponse = api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword));
        // login user
        SuccessfulLoginResponseModel loginResponse = api.login.loginUser(
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword));

        String accessToken = loginResponse.access();
        String refreshToken = loginResponse.refresh();

        String localstorageAuthBody = new LocalStorageAuthModel(registrationResponse, accessToken, refreshToken, true).toJson();

        CreateClubBodyModel clubData = new CreateClubBodyModel(
                faker.fakerBookTitle,
                faker.fakerbookAuthors,
                faker.fakerPublicationYear,
                faker.fakerDescription,
                faker.fakerTelegramChatLink
        );

        SuccessfulCreateClubResponseModel responseData = api.createClub.createClub(clubData, accessToken);

        open("/favicon.ico");
        localStorage().setItem("book_club_auth", localstorageAuthBody);
        open("/clubs/" + responseData.id());

        //wrong leave club
        $(".club-content").shouldBe(visible);
        $(".leave-btn").click();
        confirm();
        $(".error").shouldHave(text("Не удалось покинуть клуб"));
    }

    @Test
    @Disabled
    public void cantLeaveClubAsAdminTest_with_login_by_api(){
        // register user
        SuccessfulRegistrationAndGetResponseModel registrationResponse = api.registre.registerUser(
                new RegistrationBodyModel(faker.fakerUserFirstName, faker.fakerPassword));
        // login user
        SuccessfulLoginResponseModel loginResponse = api.login.loginUser(
                new LoginBodyModel(faker.fakerUserFirstName, faker.fakerPassword));

        String accessToken = loginResponse.access();
        String refreshToken = loginResponse.refresh();

        String localstorageAuthBody = """
                {
                  "user": {
                    "id": %d,
                    "username": "%s",
                    "firstName": "%s",
                    "lastName": "%s",
                    "email": "%s",
                    "remoteAddr": "%s"
                 },
                 "accessToken": "%s",
                 "refreshToken": "%s",
                 "isAuthenticated": true
                }
                """.formatted(
                registrationResponse.id(),
                registrationResponse.username(),
                registrationResponse.firstName(),
                registrationResponse.lastName(),
                registrationResponse.email(),
                registrationResponse.remoteAddr(),
                accessToken,
                refreshToken
        );

        open("/favicon.ico");
        localStorage().setItem("book_club_auth", localstorageAuthBody);

        //create club
        // todo navigation test for
        open("https://book-club.qa.guru/clubs/create");
        $("#bookTitle").setValue(faker.fakerBookTitle);
        $("#bookAuthors").setValue(faker.fakerbookAuthors);
        $("#publicationYear").setValue(String.valueOf(faker.fakerPublicationYear));
        $("#description").setValue(faker.fakerDescription);
        $("#telegramChatLink").setValue(faker.fakerTelegramChatLink).pressEnter();

        //find club
        $(".search-input").setValue(faker.fakerBookTitle);
        $(".search-button").click();

        //open club
        $(".clubs-list").$(byText(faker.fakerBookTitle)).parent().parent().$(".club-footer button").click();

        //wrong leave club
        $(".club-content").shouldBe(visible);
        $(".leave-btn").click();
        confirm();
        $(".error").shouldHave(text("Не удалось покинуть клуб"));
    }

    @Test
    @Disabled
    public void cantLeaveClubAsAdminTest_without_api(){
        // register user
        open("https://book-club.qa.guru/signup");
//        open("http://localhost:8100/signup");
        $("[data-testid=username-input]").setValue(faker.fakerUserFirstName);
        $("[data-testid=password-input]").setValue(faker.fakerPassword);
        $("[data-testid=confirm-password-input]").setValue(faker.fakerPassword).pressEnter();
        $("[data-testid=signup-button]").should(disappear);

        //login user
//        $("[data-testid=username-input]").setValue(fakerTestData.fakerUserFirstName);
//        $("[data-testid=password-input]").setValue(fakerTestData.fakerPassword).pressEnter();
//        $("[data-testid=signin-button]").should(disappear);

        //create club
        // todo navigation test for
//        open("https://book-club.qa.guru/clubs/create");
//        open("http://localhost:8100/clubs/create");
        $("[data-testid=create-club-link]").click();
        $("#bookTitle").setValue(faker.fakerBookTitle);
        $("#bookAuthors").setValue(faker.fakerbookAuthors);
        $("#publicationYear").setValue(String.valueOf(faker.fakerPublicationYear));
        $("#description").setValue(faker.fakerDescription);
        $("#telegramChatLink").setValue(faker.fakerTelegramChatLink).pressEnter();

        //find club
        $(".search-input").setValue(faker.fakerBookTitle);
        $(".search-button").click();

        //open club
        $(".clubs-list").$(byText(faker.fakerBookTitle)).parent().parent().$(".club-footer button").click();

        //wrong leave club
        $(".club-content").shouldBe(visible);
        $(".leave-btn").click();
        confirm();
        $(".error").shouldHave(text("Не удалось покинуть клуб"));
    }
}
