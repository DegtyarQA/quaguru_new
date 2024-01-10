package com.degtyar;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SearchIssueRepoSelenide {



    @BeforeAll
    static void beforeAll(){
        Configuration.baseUrl="https://github.com";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test
    void searchIssueTest() {

        open(Configuration.baseUrl);
        $(".header-search-button").click();
        $("#query-builder-test").setValue("quaguru_new").pressEnter();
        $("[data-testid=results-list] a").click();
        $("#issues-tab").click();
        $("#issue_1_link").shouldBe(visible).shouldHave(text("test issue"));
    }
}
