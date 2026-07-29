package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ClubsPage {

    // Elements
    private SelenideElement clubContent = $(".club-content");
    private SelenideElement clubsTitle = $(".club-header");
    private SelenideElement yearClub = $(".year");
    private SelenideElement authorClub = $(".authors");
    private SelenideElement descriptionClub = $(".description");
    private SelenideElement leaveButton = $(".leave-btn");
    private SelenideElement joinButton = $(".join-btn");
    private SelenideElement errorMessage = $(".error");

    // Actions
    @Step("Открываем сайт на главной")
    public ClubsPage openClubPage(String id) {
        open("/clubs/" + id);

        return this;
    }

    @Step("Отписываемся от клуба")
    public ClubsPage leaveClub() {
        leaveButton.click();

        return this;
    }

    @Step("Проверяем карточку клуба")
    public ClubsPage checkClubCards(String title, String author, String year, String description) {
        clubContent.shouldBe(visible);
        clubsTitle.shouldHave(text(title));
        yearClub.shouldHave(text(year));
        authorClub.shouldHave(text(author));
        descriptionClub.shouldHave(text(description));

        return this;
    }

    @Step("Проверяем текс ошибки выхода из своего клуба")
    public ClubsPage checkErrorMessage() {
        errorMessage.shouldHave(text("Не удалось покинуть клуб"));

        return this;
    }

    @Step("Проверяем что не состоим в клубе")
    public ClubsPage checkHasNotJoinClub() {
        joinButton.shouldBe(visible);

        return this;
    }
}
