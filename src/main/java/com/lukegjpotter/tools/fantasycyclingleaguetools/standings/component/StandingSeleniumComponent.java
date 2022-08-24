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
        WebDriver standingsWebDriver = commonWebDriverOperations.getWebDriverHeadless();
        standingsWebDriver.get("https://fantasy.road.cc/home");
        commonWebsiteOperations.login(standingsWebDriver);

        // View Competition
        commonWebsiteOperations.selectCompetition(standingsWebDriver);

        // View League
        commonWebsiteOperations.viewLeague(standingsWebDriver);

        // Determine Standings and Today's Scores
        logger.info("Determining Standings");
        StringBuilder standings = new StringBuilder("<html><head><title>Standings</title></head><body><table><tr><th>Pos.</th><th>Name</th><th>Total</th><th>Today</th></tr>");

        // Read the League Table
        List<WebElement> standingsTableRows = standingsWebDriver.findElement(By.className("leagues")).findElements(By.tagName("tr"));
        standingsTableRows.remove(0); // Remove the Table Header Row.

        // Loop - Extract Position, Name and Total Score.
        for (WebElement ridersStanding : standingsTableRows) {
            List<WebElement> standingsTableFields = ridersStanding.findElements(By.tagName("td"));
            String username = standingsTableFields.get(1).getText().trim().split(" {4}")[1];
            logger.info("Checking Standings for: {}", username);

            // Open Popup, wait for it to load, read the Score, Close Popup
            standingsTableFields.get(2).findElement(By.tagName("a")).click();
            WebElement stageResultsClose = new WebDriverWait(standingsWebDriver, Duration.ofMillis(2000)).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[3]/a")));
            String todaysScore = stageResultsClose.findElement(By.xpath("/html/body/div[2]/div[4]/div/table/tbody/tr[10]/th[2]")).getText().trim();
            stageResultsClose.click();

            standings.append("<tr><td>")
                    .append(standingsTableFields.get(0).getText().trim()) // Position
                    .append("</td><td>")
                    .append(username) // Username
                    .append("</td><td>")
                    .append(standingsTableFields.get(2).getText().trim()) // Total Score
                    .append("</td><td>")
                    .append(todaysScore)
                    .append("</td></tr>");
        }
        standings.append("</table></body></html>");

        // Cleanup
        logger.info("Finished Getting Standings");
        commonWebsiteOperations.logout(standingsWebDriver);
        standingsWebDriver.quit();

        return standings.toString();
    }
}
