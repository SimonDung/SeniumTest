package org.example.listener;

import io.qameta.allure.Attachment;
import org.example.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] saveScreenshotOnFailure() {
        return ((TakesScreenshot) BaseTest.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        saveScreenshotOnFailure();
    }
}
