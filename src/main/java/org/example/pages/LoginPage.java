package org.example.pages;

import io.qameta.allure.Step;
import org.example.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginBtn = By.cssSelector(".submit-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Login into Website")
    public void login(String username, String password) {
        fill(usernameInput, username);
        fill(passwordInput, password);
        click(loginBtn);
    }

    @Step("Retrive Error Message")
    public String getErrorMessage() {
        return getElementText(errorMessage);
    }
}
