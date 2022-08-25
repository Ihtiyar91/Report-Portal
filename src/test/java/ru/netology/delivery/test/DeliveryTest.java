package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;


class  DeliveryTest {
    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        $("[data-test-id=agreement]").click();
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        val validUser = DataGenerator.Registration.validUser("ru");
        int firstMeetingDate = 4;
        int secondMeetingDate = 7;
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(BACK_SPACE,
                        DataGenerator.generateDate(firstMeetingDate));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id=success-notification] [class='notification__content']").shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(firstMeetingDate)));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(BACK_SPACE,
                        DataGenerator.generateDate(secondMeetingDate));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id=replan-notification] .icon-button__content").click();
        $("[data-test-id=success-notification] [class='notification__content']").shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(secondMeetingDate)));
    }

    @Test
    void shouldPlanAndReplanDateEqual() {
        val validUser = DataGenerator.Registration.validUser("ru");
        int firstMeetingDate = 4;
        int secondMeetingDate = firstMeetingDate;
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(BACK_SPACE,
                        DataGenerator.generateDate(firstMeetingDate));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id=success-notification] [class='notification__content']").shouldHave(exactText("Встреча успешно запланирована на " + DataGenerator.generateDate(firstMeetingDate)));
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(BACK_SPACE,
                        DataGenerator.generateDate(secondMeetingDate));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("У вас уже запланирована встреча на эту дату. Перепланировать?")).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    @Test
    void shouldTestNoName() {
        val validUser = DataGenerator.Registration.validUser("ru");
        int firstMeetingDate = 3;
        $("[data-test-id='city'] .input__control").setValue(validUser.getCity());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(BACK_SPACE,
                        DataGenerator.generateDate(firstMeetingDate));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldTestNoCity() {
        val validUser = DataGenerator.Registration.validUser("ru");
        int firstMeetingDate = 3;
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(BACK_SPACE,
                        DataGenerator.generateDate(firstMeetingDate));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }


    @Test
    void shouldTestNoNumber() {
        val validUser = DataGenerator.Registration.validUser("ru");
        int firstMeetingDate = 3;
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(BACK_SPACE,
                        DataGenerator.generateDate(firstMeetingDate));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Поле обязательно для заполнения"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldTestYoNameRegistration() {
        val invalidUser = DataGenerator.Registration.nameYoRegistration("ru");
        int firstMeetingDate = 3;
        $("[data-test-id=city] input").setValue(invalidUser.getCity());
        $("[data-test-id=name] input").setValue(invalidUser.getName());
        $("[data-test-id=phone] input").setValue(invalidUser.getPhone());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(BACK_SPACE,
                        DataGenerator.generateDate(firstMeetingDate));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."))
                .shouldBe(Condition.visible);
    }
    @Test
    void shouldTestInvalidNameRegistration() {
        val invalidUser = DataGenerator.Registration.invalidNameRegistration("ru");
        int firstMeetingDate = 3;
        $("[data-test-id=city] input").setValue(invalidUser.getCity());
        $("[data-test-id=name] input").setValue(invalidUser.getName());
        $("[data-test-id=phone] input").setValue(invalidUser.getPhone());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(BACK_SPACE,
                        DataGenerator.generateDate(firstMeetingDate));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."))
                .shouldBe(Condition.visible);
    }
    @Test
    void shouldTestInvalidPhoneRegistration() {
        val invalidUser = DataGenerator.Registration.invalidPhoneRegistration("ru");
        int firstMeetingDate = 3;
        $("[data-test-id=city] input").setValue(invalidUser.getCity());
        $("[data-test-id=name] input").setValue(invalidUser.getName());
        $("[data-test-id=phone] input").setValue(invalidUser.getPhone());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(BACK_SPACE,
                        DataGenerator.generateDate(firstMeetingDate));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Телефон указан неверно"))
                .shouldBe(Condition.visible);
    }
    @Test
    void shouldTestInvalidCityRegistration() {
        val invalidUser = DataGenerator.Registration.invalidCityRegistration("ru");
        int firstMeetingDate = 3;
        $("[data-test-id=city] input").setValue(invalidUser.getCity());
        $("[data-test-id=name] input").setValue(invalidUser.getName());
        $("[data-test-id=phone] input").setValue(invalidUser.getPhone());
        $("[data-test-id='date'] .input__control").doubleClick();
        $("[data-test-id='date'] .input__control")
                .sendKeys(BACK_SPACE,
                        DataGenerator.generateDate(firstMeetingDate));
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Доставка в выбранный город недоступна"))
                .shouldBe(Condition.visible);
    }
}