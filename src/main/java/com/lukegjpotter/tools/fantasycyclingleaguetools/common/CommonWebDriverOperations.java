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
        options.addArguments("--remote-allow-origins=*");

        return new ChromeDriver(options);
    }

    public WebDriver getWebDriverWithUI() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        return new ChromeDriver(options);
    }
}
