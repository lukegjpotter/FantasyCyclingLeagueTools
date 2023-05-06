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

import java.util.List;

@Component
public class CommonWebsiteOperations {

    @Autowired
    private Environment env;

    private final Logger logger = LoggerFactory.getLogger(CommonWebsiteOperations.class);

    /**
     * Login to Road.cc's Website.
     *
     * @param webDriver WebDriver in use.
     *                  * @return {@code true} if login was successful, and {@code WebDriver} is on the Home/Dashboard Page.<br/>
     *                  *         {@code false} if login was not Successful, and {@code WebDriver} is on the Login Page.
     */
    public boolean login(WebDriver webDriver) {
        logger.info("Logging into Road.cc Fantasy with Username: {}", env.getProperty("ROADCC_USERNAME"));
        webDriver.findElement(By.name("user")).sendKeys(env.getProperty("ROADCC_USERNAME"));
        webDriver.findElement(By.name("pass")).sendKeys(env.getProperty("ROADCC_PASSWORD"));
        webDriver.findElement(By.className("login-submit")).click();

        // Check Login was Successful.
        if (!webDriver.findElement(By.className("login-bar")).getText().contains("Logged in as " + env.getProperty("ROADCC_USERNAME"))) {
            logger.error("Your Username and/or Password were not correct, or there is some other problem. Please check ROADCC_USERNAME and ROADCC_PASSWORD.");
            return false;
        }

        return true;
    }

    public void logout(WebDriver webDriver) {
        logger.info("Logging out of Road.cc Fantasy.");
        webDriver.findElement(By.xpath("//a[@href='/logout']")).click();
    }

    public void openRoadCcFantasyWebsite(WebDriver webDriver) {
        webDriver.get("https://fantasy.road.cc/home");
    }

    /**
     * Selects the most recent Active Competition, as denoted by the "View" button.
     *
     * @param webDriver WebDriver in use.
     * @return {@code true} if Joined Competitions Are Available, and {@code WebDriver} is on the Active Competition Page.<br/>
     * {@code false} if Joined Competitions Are Not Available, and {@code WebDriver} is on the Home/Dashboard Page.
     */
    public boolean selectCompetition(WebDriver webDriver) {
        logger.info("Selecting Competition");

        WebElement competitionWebElement = webDriver.findElement(By.xpath("//div[@class=\"compbox joinedcomp last\"]"));

        try {
            competitionWebElement.findElement(By.className("joinbutton")).click();
        } catch (NoSuchElementException e) {
            logger.error("You have not joined any Competitions, or Competitions you have joined in the past are no longer active.");
            return false;
        }

        return true;
    }

    /**
     * Selects the League as specified in the {@code ROADCC_LEAGUE_NAME} Environment Variable.
     *
     * @param webDriver WebDriver in use.
     * @return {@code true} if League is Available, and {@code WebDriver} is on the Active League Page.<br/>
     * {@code false} if League is not Available, and {@code WebDriver} is on the Active Competition Page.
     */
    public boolean viewLeague(WebDriver webDriver) {
        logger.info("Viewing League: {}", env.getProperty("ROADCC_LEAGUE_NAME"));
        try {
            webDriver.findElement(By.id("leaguelist-current")).findElement(By.partialLinkText(env.getProperty("ROADCC_LEAGUE_NAME"))).click();
        } catch (NoSuchElementException e) {
            logger.error("You have not joined any Leagues, or Leagues you have joined in the past are no longer active, or there is a Typo in the ROADCC_LEAGUE_NAME Environment Variable value.");
            return false;
        }

        return true;
    }

    public String getUsernameFromLeagueTable(List<WebElement> tableFields) {
        return tableFields.get(1).getText().trim().split(" {4}")[1];
    }
}
