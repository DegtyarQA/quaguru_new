package com.degtyar;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.attachment;
import static io.qameta.allure.Allure.step;

public class SearchIssueRepoLambdaAttachment {
    private static final String QUAGURU_NEW = "quaguru_new";

    @BeforeAll
    static void beforeAll(){
        Configuration.baseUrl="https://github.com";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }


    @Feature("Issue in git")
    @Test
    void searchIssuelambdaTest() {

        step("Открываем главную страницу", () -> {
            open(Configuration.baseUrl);
            attachment("Source", webdriver().driver().source());
        });
        step("Ставим курсор в поле поиска", () -> {
            $(".header-search-button").click();
        });
        step("Ищем репозиторий", () -> {
            $("#query-builder-test").setValue(QUAGURU_NEW).pressEnter();
            $("[data-testid=results-list] a").click();
        });
        step("Открываем репозиторий по результату поиска" + QUAGURU_NEW, () -> {
            $("#issues-tab").click();
        });
        step("Проверяем наличие ишью", new Allure.ThrowableContextRunnableVoid<Allure.StepContext>() { //Это тоже самое что лямбда в шагах выше
            @Override
            public void run(Allure.StepContext context) throws Throwable {
                $("#issue_1_link").shouldBe(visible).shouldHave(text("test issue"));
            }
        });
    }
}
