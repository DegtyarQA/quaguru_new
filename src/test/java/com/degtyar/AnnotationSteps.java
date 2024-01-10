package com.degtyar;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AnnotationSteps {

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://github.com";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Feature("Issue in git")
    @Owner("Degtyar Valery")
    @Severity(SeverityLevel.MINOR)
    @Story("Что это????")
    @Link (value = "Проверка", url = "https://ru.wikihow.com/")
    @DisplayName("Поиск issue в репозитории")
    @Test
    void searchIssueAnnotationStepsTest() {
        WebSteps searchRepo = new WebSteps();

        searchRepo.openPage()
                .searchRepo()
                .checkIssue()
                .takeScreenShot();
    }
}

