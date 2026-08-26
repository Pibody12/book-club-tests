package steps;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.localStorage;
import static com.codeborne.selenide.Selenide.open;

public class GeneralSteps {

    @Step("Открытие браузера под авторизованным пользователем")
    public void authInBrowser (String localstorageAuthBody) {
        open("/favicon.ico");
        localStorage().setItem("book_club_auth", localstorageAuthBody);
    }
}
