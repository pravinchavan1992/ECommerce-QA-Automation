package com.practice;

import com.config.App;
import com.factory.DriverFactory;
import com.config.ConfigurationManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.ByteArrayInputStream;

public class BaseTest {

    @Parameters({"browser", "environment"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, @Optional("qa") String environment) {
        // Step 1: Resolve environment
        String environmentFromCli = System.getProperty("environment");
        if(environmentFromCli != null) {
            System.setProperty("environment", environmentFromCli);
        }
        else {
            System.setProperty("environment", environment);
        }

        // Step 2: Resolve browser
        String browserFromCli = App.browser;
        String browserFromConfig = ConfigurationManager.getInstance().getProperty("browser");
        String finalBrowser = resolveValue(browserFromCli, browser, browserFromConfig);

        Allure.step("Selected browser: " + finalBrowser);
        System.out.println("✅ Browser selected: " + finalBrowser);

        // Allure Attachment for reporting
        Allure.addAttachment("Browser", finalBrowser);
        Allure.addAttachment("Environment", System.getProperty("environment"));

        // Step 3: Initialize driver
        DriverFactory.initDriver(finalBrowser);

        String url = ConfigurationManager.getInstance().getProperty("url");
        Allure.step("Launching Application URL: " + url);
        DriverFactory.getDriver().get(url);
    }
    private static String resolveValue(String cliValue, String xmlValue, String configValue) {
        if (isValid(cliValue)) {
            return cliValue;
        } else if (isValid(xmlValue)) {
            return xmlValue;
        } else if (isValid(configValue)) {
            return configValue;
        }
        else {
            throw new IllegalArgumentException("No valid value found");
        }
    }

    private static boolean isValid(String value) {
        return value != null && !value.isBlank();
    }
    @AfterMethod
    public void tearDown(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Screenshot on Failure", new ByteArrayInputStream(screenshot));
        }
        DriverFactory.closeDriver();
    }

}
