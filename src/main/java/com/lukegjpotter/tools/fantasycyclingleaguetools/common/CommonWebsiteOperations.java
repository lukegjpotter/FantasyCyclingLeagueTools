package com.lukegjpotter.tools.fantasycyclingleaguetools.common;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CommonWebsiteOperations {

    @Autowired
    private Environment env;

    private final Logger logger = LoggerFactory.getLogger(CommonWebsiteOperations.class);

    public void login(WebDriver webDriver) {
        logger.info("Logging into Road.cc Fantasy with Username: {}", env.getProperty("ROADCC_USERNAME"));
        webDriver.findElement(By.name("user")).sendKeys(env.getProperty("ROADCC_USERNAME"));
        webDriver.findElement(By.name("pass")).sendKeys(env.getProperty("ROADCC_PASSWORD"));
        webDriver.findElement(By.className("login-submit")).click();
    }

    public void logout(WebDriver webDriver) {
        logger.info("Logging out of Road.cc Fantasy.");
        webDriver.findElement(By.xpath("//a[@href='/logout']")).click();
    }

    public boolean selectCompetition(WebDriver webDriver) {
        logger.info("Selecting Competition");
        boolean isRaceOver = false;

        WebElement competitionWebElement = webDriver.findElement(By.xpath("//div[@class=\"compbox joinedcomp last\"]"));
        try {
            isRaceOver = competitionWebElement.findElement(By.className("ribbon-grey")).getText().trim().equals("ENDED");
        } catch (NoSuchElementException noSuchElementException) {
            // Ignore, as competition is ongoing.
        }
        competitionWebElement.findElement(By.className("joinbutton")).click();

        return isRaceOver;
    }

    public void viewLeague(WebDriver webDriver) {
        logger.info("Viewing League: {}", env.getProperty("ROADCC_LEAGUE_NAME"));
        webDriver.findElement(By.id("leaguelist-current")).findElement(By.partialLinkText(env.getProperty("ROADCC_LEAGUE_NAME"))).click();
    }
}
