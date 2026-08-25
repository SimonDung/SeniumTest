package org.test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.example.base.BaseTest;
import org.example.data.JsonDataProvider;
import org.example.pages.LoginPage;
import org.example.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = JsonDataProvider.class)
    @Epic("User Authentication")
    @Feature("Login Module")
    @Description("Verify user can login with valid credential and cannot login with invalid credential.")
    public void testLogin(String username, String password, Boolean loginStatus) throws InterruptedException {
        pages().loginPage()
                .login(username, password);

        if (loginStatus.equals(true)) {
            Assert.assertEquals(pages().productPage()
                    .getPageTitleText(), "Products", "Login failed or title mismatched!");
        } else {
            Assert.assertEquals(pages()
                    .loginPage().getErrorMessage(), "Epic sadface: Sorry, this user has been locked out.", "Warning message not shown");
        }
    }
}
