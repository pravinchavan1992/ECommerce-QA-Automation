package com.utility;

import com.factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;
import java.util.Set;

public class ElementUtils {

    private static WebDriver driver() {
        return DriverFactory.getDriver();
    }

    private static WebDriverWait webDriverWait() {
        return new WebDriverWait(driver(), Duration.ofSeconds(10));
    }

    public static WebElement waitForElementTobeClickable(By locator) {
        return webDriverWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForElementTobeClickable(WebElement element) {
        return webDriverWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForElementTobeVisible(By locator) {
        return webDriverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static List<WebElement> waitForElementsTobeVisible(By locator) {
        return webDriverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public static List<WebElement> waitForElementsPresence(By locator) {
        return webDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public static WebElement waitForElementsToPresence(By locator) {
        return webDriverWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void click(By locator) {
        waitForElementTobeClickable(locator).click();
    }

    public static void click(WebElement element) {
        WebElement ele= waitForElementTobeClickable(element);
        waitForElementTobeClickable(element).click();
    }

    public static void enterText(By locator, String text) {
        WebElement element = waitForElementTobeClickable(locator);
        element.clear();
        element.sendKeys(text);
    }

    public static String getText(By locator) {
        return waitForElementTobeVisible(locator).getText().trim();
    }

    public static boolean isEnabled(By locator) {
        return waitForElementTobeVisible(locator).isEnabled();
    }

    private static Set<String> getWindowHandels(){
        return ElementUtils.driver().getWindowHandles();
    }

    private static String getWindowHandel(){
        return ElementUtils.driver().getWindowHandle();
    }

    public static void SwitchToNewTab(){
        String currentWindowHandle = getWindowHandel();
        Set<String> windowHandles = getWindowHandels();
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(currentWindowHandle)) {
                driver().switchTo().window(windowHandle);
            }
        }
    }

    public static void selectFromDropDown(By locator, String option) {
        WebElement element = waitForElementTobeClickable(locator);
        Select select = new Select(element);
        select.selectByValue(option);
    }
}
