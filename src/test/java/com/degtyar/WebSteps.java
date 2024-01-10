package com.degtyar;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebSteps {
    private static final String QUAGURU_NEW1 = "quaguru_new";

    @Step("Открываем страницу")
    public WebSteps openPage() {
        open(Configuration.baseUrl);
        return this;
    }

    @Step("Ищем репозиторий")
    public WebSteps searchRepo() {
        $(".header-search-button").click();
        $("#query-builder-test").setValue(QUAGURU_NEW1).pressEnter();
        $("[data-testid=results-list] a").click();
        return this;
    }

    @Step("Делаем проверку")
    public WebSteps checkIssue() {
        $("#issues-tab").click();
        $("#issue_1_link").shouldBe(visible).shouldHave(text("test issue"));
        return this;
    }

    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] takeScreenShot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

}
