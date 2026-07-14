package tests;

import base.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ClubTest extends TestBase {

    String userName = "Tim4224";
    String passwordName = "qweasd";



    @Test
    public void cantLeaveClubAsAdminTest(){
        // register user
        open("https://book-club.qa.guru/signup");
//        open("http://localhost:8100/signup");
        $("[data-testid=username-input]").setValue(fakerTestData.fakerUserFirstName);
        $("[data-testid=password-input]").setValue(fakerTestData.fakerPassword);
        $("[data-testid=confirm-password-input]").setValue(fakerTestData.fakerPassword).pressEnter();
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
        $("#bookTitle").setValue(fakerTestData.fakerBookTitle);
        $("#bookAuthors").setValue(fakerTestData.fakerbookAuthors);
        $("#publicationYear").setValue(String.valueOf(fakerTestData.fakerPublicationYear));
        $("#description").setValue(fakerTestData.fakerDescription);
        $("#telegramChatLink").setValue(fakerTestData.fakerTelegramChatLink).pressEnter();

        //find club
        $(".search-input").setValue(fakerTestData.fakerBookTitle);
        $(".search-button").click();

        //open club
        $(".clubs-list").$(byText(fakerTestData.fakerBookTitle)).parent().parent().$(".club-footer button").click();

        //wrong leave club
        $(".club-content").shouldBe(visible);
        $(".leave-btn").click();
        confirm();
        $(".error").shouldHave(text("Не удалось покинуть клуб"));
    }
}
