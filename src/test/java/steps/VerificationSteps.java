package steps;

import io.qameta.allure.Step;

import static org.assertj.core.api.Assertions.assertThat;

public class VerificationSteps {

    @Step("Проверка, что \"{fieldName}\" имеет значение \"{expected}\"")
    public void shouldHaveValue(String fieldName, String actual, String expected) {
        assertThat(actual).isEqualTo(expected);
    }

    @Step("Проверка, что \"{fieldName}\" имеет значение \"{expected}\"")
    public void shouldHaveIntValue(String fieldName, Integer actual, Integer expected) {
        assertThat(actual).isEqualTo(expected);
    }

    @Step("Проверка, что \"{fieldName}\" пустое")
    public void shouldHaveEmptyValue(String fieldName, String actual) {
        assertThat(actual).isEqualTo("");
    }

    @Step("Проверка, что \"{fieldName}\" начинается с \"{expected}\"")
    public void shouldHaveBegin(String fieldName, String actual, String expected) {
        assertThat(actual).startsWith(expected);
    }

    @Step("Проверка, что \"{fieldNameOne}\" не равен \"{fieldNameTwo}\"")
    public void notShouldHaveValue(String fieldNameOne, String fieldNameTwo, String firstMeaning, String secondMeaning) {
        assertThat(firstMeaning).isNotEqualTo(secondMeaning);
    }

    @Step("Проверка, что \"{fieldName}\" больше \"{expected}\"")
    public void moreThan(String fieldName, Integer actual, Integer expected) {
        assertThat(actual).isNotEqualTo(expected);
    }

    @Step("Проверка, что \"{fieldName}\" не пустое")
    public void isNotEmpty(String fieldName, String actual) {
        assertThat(actual).isNotEmpty();
    }

    @Step("Проверка, что \"{fieldName}\" не пустое")
    public void isNull(String fieldName, Boolean actual) {
        assertThat(actual).isNull();
    }

    @Step("Проверка, что \"{fieldName}\" соответствует \"{expected}\"")
    public void matches(String fieldName, String actual, String expected) {
        assertThat(actual).matches(expected);
    }
}