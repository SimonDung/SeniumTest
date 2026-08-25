package org.example.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.config.ConfigManager;
import org.example.pages.manager.PageObjectManager;
import org.example.utils.VideoManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(String browser, Method method) throws MalformedURLException {
        // 1. Start video recording named after the current test method
//        VideoManager.startRecording(method);

        WebDriverManager.chromedriver().setup();
        // Launch a distinct ChromeDriver instance for the current executing thread
        WebDriver driver;
        String gridUrl = ConfigManager.get("grid.url"); // URL of your Grid Hub

        if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            // Enable VNC (Live View) and Video Recording on Grid
//            options.setCapability("se:vncEnabled", true);
//            options.setCapability("se:recordVideo", true);
            driver = new RemoteWebDriver(new URL(gridUrl), options);
        } else {
            ChromeOptions options = new ChromeOptions();
            driver = new RemoteWebDriver(new URL(gridUrl), options);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.get(ConfigManager.get("app.url"));
        driverThreadLocal.set(driver);
    }

    public PageObjectManager pages() {
        return new PageObjectManager(getDriver());
    }

    @AfterMethod
    public void tearDown() {
        // Clean up the browser and clear the ThreadLocal memory leak
        if (getDriver() != null) {
            getDriver().quit();
            driverThreadLocal.remove();
        }
    }

}
