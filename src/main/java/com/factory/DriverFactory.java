package com.factory;

import com.config.App;
import com.constants.Browser;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> threadLocal = new ThreadLocal<>();

    public static void initDriver(String browser) {
        if (Objects.isNull(threadLocal.get())) {
            final boolean isRemote = "remote".equalsIgnoreCase(App.runType);

            Browser browserName;
            try {
                browserName = Browser.valueOf(browser.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid browser: " + browser +
                        ". Supported browsers: CHROME, FIREFOX, EDGE, SAFARI");
            }

            MutableCapabilities capabilities = DriverOptions.getOptionsForBrowser(browser.toUpperCase());

            WebDriver driver = switch (browserName) {
                case CHROME -> isRemote
                        ? createRemoteDriver(capabilities)
                        : new ChromeDriver((ChromeOptions) capabilities);

                case FIREFOX -> isRemote
                        ? createRemoteDriver(capabilities)
                        : new FirefoxDriver((FirefoxOptions) capabilities);

                case EDGE -> isRemote
                        ? createRemoteDriver(capabilities)
                        : new EdgeDriver((EdgeOptions) capabilities);

                case SAFARI -> new SafariDriver();
            };

            threadLocal.set(driver);
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            System.out.println("✅ [" + Thread.currentThread().getName() + "] Launched Browser: " + browserName);
        }
    }

    private static RemoteWebDriver createRemoteDriver(MutableCapabilities options) {
        try {
            return new RemoteWebDriver(new URL(App.gridUrl), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid grid URL: " + System.getProperty("gridUrl"), e);
        }
    }

    public static WebDriver getDriver() {
        if (Objects.isNull(threadLocal.get())) {
            throw new IllegalStateException("Driver not initialized");
        }
        return threadLocal.get();
    }

    public static void closeDriver() {
        WebDriver driver = threadLocal.get();
        if (driver != null) {
            driver.quit();
            System.out.println("🛑 [" + Thread.currentThread().getName() + "] Browser closed.");
            threadLocal.remove();
        } else {
            System.out.println("⚠️ [" + Thread.currentThread().getName() + "] No browser instance found to close.");
        }
    }
}
