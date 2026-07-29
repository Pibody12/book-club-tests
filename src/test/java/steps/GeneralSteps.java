package steps;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.localStorage;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

public class GeneralSteps {

    @Step("Открытие браузера под авторизованным пользователем")
    public void authInBrowser (String localstorageAuthBody) {
        open("/favicon.ico");
        localStorage().setItem("book_club_auth", localstorageAuthBody);
    }
}
