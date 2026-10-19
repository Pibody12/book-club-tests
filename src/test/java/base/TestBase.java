package base;

import api.ApiClient;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import pages.*;
import steps.GeneralSteps;
import steps.VerificationSteps;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import test_data.FakerTestData;

public class TestBase {

    public MainPage mainPage = new MainPage();
    public RegistrationPage regPage = new RegistrationPage();
    public LoginPage loginPage = new LoginPage();
    public CreateClubPage createClubPage = new CreateClubPage();
    public ClubsPage clubsPage = new ClubsPage();

    protected FakerTestData faker;
    protected static final ApiClient api = new ApiClient();
    protected static final VerificationSteps verificationSteps = new VerificationSteps();
    protected static final GeneralSteps generalSteps = new GeneralSteps();

    @BeforeAll
    static public void setUp() {
        RestAssured.baseURI = "https://book-club.qa.guru/";
//        RestAssured.baseURI = "http://localhost:8100/";
        RestAssured.basePath = "/api/v1";

        Configuration.baseUrl = "https://book-club.qa.guru";
//        Configuration.baseUrl = "http://localhost:8100/";
        Configuration.browserSize = "1920x1080";

    }

    @BeforeEach
    void initFaker() {
        faker = new FakerTestData();
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }
}