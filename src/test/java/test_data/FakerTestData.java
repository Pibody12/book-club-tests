package test_data;

import net.datafaker.Faker;
import java.util.Locale;

public class FakerTestData {
    Faker faker = new Faker();
    Faker fakerRu = new Faker(new Locale("ru"));

    public String
            fakerUserFirstName = faker.name().firstName(),
            fakerInvalideName = faker.name().fullName(),
            fakerPassword = String.valueOf(faker.random().nextInt(9999999)),
            fakerBookTitle = faker.book().title(),
            fakerbookAuthors = faker.book().author(),
            fakerDescription = faker.chuckNorris().fact(),
            fakerTelegramChatLink = "https://t.me/" + faker.name().firstName();

    public Integer
            fakerPublicationYear = faker.random().nextInt(1800, 2026);
}