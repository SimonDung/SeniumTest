package org.test;

import org.example.base.BaseTest;
import org.example.data.JsonDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

public class ProductTest extends BaseTest {
    @BeforeMethod
    public void loginWeb() {
        JsonDataProvider jsonDataProvider = new JsonDataProvider();
        Object[][] data = jsonDataProvider.loginData();
        String username = data[0][0].toString();
        String password = data[0][1].toString();
        pages().loginPage()
                .login(username, password);
    }

    @Test
    public void verifyAllProductInPage() throws InterruptedException {
        pages().productPage().waitUntilLoaded();
        Thread.sleep(10000);
    }
}
