package com.lukegjpotter.tools.fantasycyclingleaguetools.componentsfortransferservice;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class TransferSeleniumComponent {

    @Autowired
    Environment env;

    Logger logger = LoggerFactory.getLogger(TransferSeleniumComponent.class);

    public String getTransfers() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        WebDriver transfersWebDriver = new ChromeDriver(options);

        // Login using Environmental Variables: ROADCC_USERNAME & ROADCC_PASSWORD
        logger.info("Logging into Road.cc Fantasy with Username: " + env.getProperty("ROADCC_USERNAME"));
        transfersWebDriver.get("https://fantasy.road.cc/home");
        transfersWebDriver.findElement(By.name("user")).sendKeys(env.getProperty("ROADCC_USERNAME"));
        transfersWebDriver.findElement(By.name("pass")).sendKeys(env.getProperty("ROADCC_PASSWORD"));
        transfersWebDriver.findElement(By.className("login-submit")).click();

        // Select Competition - Giro, Tour, Vuelta.
        logger.info("Page Title: " + transfersWebDriver.getTitle());

        // Determine the Latest Stage
        // View League
        // Loop - Open Each User
        // Expand Transfers
        // Format output

        transfersWebDriver.quit();

        return "Not Implemented\n";
    }
}
