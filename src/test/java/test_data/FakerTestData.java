package test_data;

import net.datafaker.Faker;
import java.util.Locale;

public class FakerTestData {
    Faker faker = new Faker();
    Faker fakerRu = new Faker(new Locale("ru"));

    public String fakerUserFirstName = faker.name().firstName(),
            fakerUserLastName = faker.name().lastName(),
            fakerInvalideName = faker.name().fullName(),
            fakerPassword = String.valueOf(faker.random().nextInt(9999999));
}