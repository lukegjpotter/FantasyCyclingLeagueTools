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
        commonWebsiteOperations.openRoadCcFantasyWebsite(standingsWebDriver);

        boolean isLoginSuccessful = commonWebsiteOperations.login(standingsWebDriver);
        if (!isLoginSuccessful) return "";

        // View Competition
        boolean isJoinedCompetitionsAvailable = commonWebsiteOperations.selectCompetition(standingsWebDriver);
        if (!isJoinedCompetitionsAvailable) return "";

        // View League
        boolean isJoinedLeaguesAvailable = commonWebsiteOperations.viewLeague(standingsWebDriver);
        if (!isJoinedLeaguesAvailable) return "";

        // Determine Standings and Today's Scores
        logger.info("Determining Standings");
        StringBuilder standings = new StringBuilder("<html><head><title>Standings</title></head><body><table><tr><th>Pos.</th><th>Name</th><th>Total</th><th>Stage %s</th></tr>");
        int stageNumber = 0;

        // Read the League Table
        List<WebElement> standingsTableRows = standingsWebDriver.findElement(By.className("leagues")).findElements(By.tagName("tr"));
        standingsTableRows.remove(0); // Remove the Table Header Row.

        // Loop - Extract Position, Name and Total Score.
        for (WebElement ridersStanding : standingsTableRows) {
            List<WebElement> standingsTableFields = ridersStanding.findElements(By.tagName("td"));
            String username = commonWebsiteOperations.getUsernameFromLeagueTable(standingsTableFields);
            logger.info("Checking Standings for: {}", username);

            // Open Popup, wait for it to load, read the Score, Close Popup
            standingsTableFields.get(2).findElement(By.tagName("a")).click();
            WebDriverWait stageResultsCloseWebDriverWait = new WebDriverWait(standingsWebDriver, Duration.ofSeconds(10));
            WebElement stageResultsClose = stageResultsCloseWebDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[3]/a")));

            String todaysScore = stageResultsClose.findElement(By.xpath("/html/body/div[2]/div[4]/div/table/tbody/tr[10]/th[2]")).getText().trim();

            if (stageNumber == 0) {
                String teamRaceandStageNumber = stageResultsClose.findElement(By.xpath("//*[@id=\"overlay\"]/div[1]/h3")).getText().trim();
                stageNumber = Integer.parseInt(teamRaceandStageNumber.substring(teamRaceandStageNumber.lastIndexOf(" ") + 1, teamRaceandStageNumber.lastIndexOf(".")));
            }

            // Close Popup
            stageResultsClose.click();

            String leaguePosition = standingsTableFields.get(0).getText().trim();
            String totalScore = standingsTableFields.get(2).getText().trim();
            standings.append("<tr><td style=\"text-align:right;\"><code>")
                    .append(leaguePosition)
                    .append("</code></td><td>")
                    .append(username)
                    .append("</td><td style=\"text-align:right;\"><code>")
                    .append(totalScore)
                    .append("</code></td><td style=\"text-align:right;\"><code>")
                    .append(todaysScore)
                    .append("</code></td></tr>");
        }
        standings.append("</table></body></html>");

        // Cleanup
        logger.info("Finished Getting Standings");
        commonWebsiteOperations.logout(standingsWebDriver);
        standingsWebDriver.quit();

        return String.format(standings.toString(), stageNumber);
    }
}
