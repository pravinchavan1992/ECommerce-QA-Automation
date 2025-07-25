package com.config;

public class App {
    public static String runType = System.getProperty("runType", "local");
    public static String gridUrl = System.getProperty("gridUrl", "http://localhost:4444/wd/hub");
    public static String browser = System.getProperty("browser", "firefox");
}
