package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import org.openqa.selenium.Keys;

public class CardDeliveryTest {

    private String generateDate(int daysToAdd) {
        return LocalDate.now()
                .plusDays(daysToAdd)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999"); // URL приложения
    }

    @Test
    void shouldSubmitRequestSuccessfully() {
        String planningDate = generateDate(3);

        SelenideElement cityInput = $("[data-test-id=city] input");
        SelenideElement dateInput = $("[data-test-id=date] input");
        SelenideElement nameInput = $("[data-test-id=name] input");
        SelenideElement phoneInput = $("[data-test-id=phone] input");
        SelenideElement agreementCheckbox = $("[data-test-id=agreement]");
        SelenideElement submitButton = $("button.button");

        cityInput.setValue("Москва");

        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        dateInput.setValue(planningDate);

        nameInput.setValue("Иванов-Петров Сергей");

        phoneInput.setValue("+79991234567");

        agreementCheckbox.click();

        submitButton.click();

        submitButton.shouldBe(disabled);

        $("[data-test-id=notification]")
                .should(appear, Duration.ofSeconds(15))
                .shouldHave(text("Успешно! Встреча успешно забронирована"));

        submitButton.shouldBe(enabled);
    }
}
