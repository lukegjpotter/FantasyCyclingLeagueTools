package com.lukegjpotter.tools.fantasycyclingleaguetools.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
public class CommonWebDriverOperations {

    public WebDriver getWebDriverHeadless() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        return new ChromeDriver(options);
    }

    public WebDriver getWebDriverWithUI() {
        return new ChromeDriver(new ChromeOptions());
    }
}
