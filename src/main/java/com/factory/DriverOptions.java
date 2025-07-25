package com.factory;

import com.config.ConfigurationManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverOptions {

    private static final boolean HEADLESS = Boolean.parseBoolean(ConfigurationManager.getInstance().getProperty("headless"));

    private static final String runType = System.getProperty("runType");

    private static final boolean isRemote = "remote".equalsIgnoreCase(runType);

    public static MutableCapabilities getOptionsForBrowser(String browser) {
        return switch (browser.toLowerCase()) {
            case "chrome" -> chromeOptions();
            case "firefox" -> firefoxOptions();
            case "edge" -> edgeOptions();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }


    private static ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments(
                "--remote-allow-origins=*",
                "--disable-notifications",
                "--disable-extensions",
                "--incognito",
                "--disable-blink-features=AutomationControlled"
        );
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        if (HEADLESS || isRemote) {
            options.addArguments("--headless=new", "--window-size=1920,1080", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        } else {
            options.addArguments("--start-maximized");
        }



        if (isRemote) {
            options.setCapability("browserName", "chrome");
            options.setCapability("platformName", "Linux");
            options.setCapability("browserVersion", "latest");
        }

        return options;
    }

    private static FirefoxOptions firefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();

        if (HEADLESS || isRemote) options.addArguments("-headless");

        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("media.navigator.permission.disabled", true);

        if (isRemote) {
            options.setCapability("browserName", "firefox");
            options.setCapability("platformName", "Linux");
            options.setCapability("browserVersion", "latest");
        }

        return options;
    }

    private static EdgeOptions edgeOptions() {
        EdgeOptions options = new EdgeOptions();

        if (HEADLESS || isRemote) {
            options.addArguments("--headless=new", "--window-size=1920,1080", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
        } else {
            options.addArguments("--start-maximized");
        }

        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--disable-notifications", "--disable-extensions", "--incognito");

        if (isRemote) {
            options.setCapability("browserName", "MicrosoftEdge");
            options.setCapability("platformName", "Windows 11");
            options.setCapability("browserVersion", "latest");
        }

        return options;
    }
}
