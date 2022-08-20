package com.lukegjpotter.tools.fantasycyclingleaguetools.standings.component;

import com.lukegjpotter.tools.fantasycyclingleaguetools.common.CommonWebDriverOperations;
import com.lukegjpotter.tools.fantasycyclingleaguetools.common.CommonWebsiteOperations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class StandingSeleniumComponent {

    @Autowired
    private CommonWebsiteOperations commonWebsiteOperations;
    @Autowired
    private CommonWebDriverOperations commonWebDriverOperations;

    private final Logger logger = LoggerFactory.getLogger(StandingSeleniumComponent.class);

    public String getStandings() {

        // Open website and login.
        WebDriver standingsWebDriver = commonWebDriverOperations.getWebDriverWithUI();
        standingsWebDriver.get("https://fantasy.road.cc/home");
        commonWebsiteOperations.login(standingsWebDriver);

        // View Competition
        commonWebsiteOperations.selectCompetition(standingsWebDriver);

        // View League
        commonWebsiteOperations.viewLeague(standingsWebDriver);

        // Determine Standings and Today's Scores
        logger.info("Determining Standings");
        // ToDo - Incorporate HTML Table Output.
        StringBuilder standings = new StringBuilder("Pos.\tName\t\tTotal\tToday\n");

        // Read the League Table
        List<WebElement> standingsTableRows = standingsWebDriver.findElement(By.className("leagues")).findElements(By.tagName("tr"));
        standingsTableRows.remove(0); // Remove the Table Header Row.

        // Loop - Extract Position, Name and Total Score.
        for (WebElement ridersStanding : standingsTableRows) {
            List<WebElement> standingsTableFields = ridersStanding.findElements(By.tagName("td"));
            // Open Popup, wait for it to load, read the Score.
            standingsTableFields.get(2).click();
            WebElement stageResultsPopup = new WebDriverWait(standingsWebDriver, Duration.ofMillis(500)).until(ExpectedConditions.presenceOfElementLocated(By.className("ro-mainstats")));
            String todaysScore = stageResultsPopup.findElements(By.tagName("tr")).get(9).findElements(By.tagName("th")).get(1).getText().trim();
            // Close Popup
            standingsWebDriver.findElement(By.id("overlay")).findElement(By.id("overlay-hide")).click();

            standings.append(standingsTableFields.get(0).getText().trim()) // Position
                    .append("\t")
                    .append(standingsTableFields.get(1).getText().trim().split(" {4}")[1]) // Username
                    .append("\t")
                    .append(standingsTableFields.get(2).getText().trim()) // Total Score
                    .append("\t")
                    .append(todaysScore)
                    .append("\n");
        }

        // Cleanup
        commonWebsiteOperations.logout(standingsWebDriver);
        standingsWebDriver.quit();
        logger.info("Finished Getting Standings");

        return standings.toString();
    }
}
