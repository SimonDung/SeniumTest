package org.example.pages;

import io.qameta.allure.Step;
import org.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {

    private final By pageTitle = By.cssSelector("span.title");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @Step("Retrieve Page title text")
    public String getPageTitleText() {
        return getElementText(pageTitle);
    }

    @Step("Wait until Product Page loaded successfully")
    public void waitUntilLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).isDisplayed();
    }
}
