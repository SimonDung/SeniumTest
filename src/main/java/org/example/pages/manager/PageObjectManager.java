package org.example.pages.manager;

import org.example.pages.LoginPage;
import org.example.pages.ProductPage;
import org.openqa.selenium.WebDriver;

public class PageObjectManager {
    private final WebDriver driver;


    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage loginPage() {
        return new LoginPage(driver);
    }

    public ProductPage productPage() {
        return new ProductPage(driver);
    }
}
