package com.lukegjpotter.tools.fantasycyclingleaguetools.teams.component;

import com.lukegjpotter.tools.fantasycyclingleaguetools.common.CommonWebDriverOperations;
import com.lukegjpotter.tools.fantasycyclingleaguetools.common.CommonWebsiteOperations;
import com.lukegjpotter.tools.fantasycyclingleaguetools.teams.model.UsersTeams;
import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.service.FantasyCyclingLeagueTransferService;
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
public class TeamSeleniumComponent {

    @Autowired
    private CommonWebsiteOperations commonWebsiteOperations;
    @Autowired
    private CommonWebDriverOperations commonWebDriverOperations;
    @Autowired
    private FantasyCyclingLeagueTransferService transferService;
    @Autowired
    private TransferMergeComponent transferMergeComponent;

    private final Logger logger = LoggerFactory.getLogger(TeamSeleniumComponent.class);

    public String getTeams() {
        // Open website and login.
        WebDriver teamsWebDriver = commonWebDriverOperations.getWebDriverHeadless();
        commonWebsiteOperations.openRoadCcFantasyWebsite(teamsWebDriver);
        commonWebsiteOperations.login(teamsWebDriver);

        // View Competition
        commonWebsiteOperations.selectCompetition(teamsWebDriver);

        // View League
        commonWebsiteOperations.viewLeague(teamsWebDriver);

        // Determine Standings and Today's Scores
        logger.info("Determining Existing Teams");
        StringBuilder teamsStringBuilder = new StringBuilder("<html><head><title>Teams</title></head><body>");
        UsersTeams usersTeams = determineUsersTeams(teamsWebDriver);

        // Cleanup
        logger.info("Determined Teams");
        commonWebsiteOperations.logout(teamsWebDriver);
        teamsWebDriver.quit();


        // Merge Transfers.
        String transfersHtmlSource = transferService.getTransfers();
        usersTeams = transferMergeComponent.mergeTransfersIntoUsersTeams(transfersHtmlSource, usersTeams);

        // Align Teams
        usersTeams.alignTeams();

        teamsStringBuilder.append(usersTeams).append("</body></html>");
        logger.info("Finished Getting Teams");

        return teamsStringBuilder.toString();
    }

    private UsersTeams determineUsersTeams(WebDriver determineTeamsWebDriver) {
        UsersTeams usersTeams = new UsersTeams();
        // Read the League Table
        List<WebElement> standingsTableRows = determineTeamsWebDriver.findElement(By.className("leagues")).findElements(By.tagName("tr"));
        standingsTableRows.remove(0); // Remove the Table Header Row.

        // Loop - Click on Total Score, Read Rider's Surnames.
        for (WebElement ridersStanding : standingsTableRows) {
            List<WebElement> standingsTableFields = ridersStanding.findElements(By.tagName("td"));
            String username = commonWebsiteOperations.getUsernameFromLeagueTable(standingsTableFields);
            logger.info("Checking Team for: {}", username);
            usersTeams.addUser(username);

            // Open Popup, wait for it to load, read the Score, Close Popup
            standingsTableFields.get(2).findElement(By.tagName("a")).click();
            WebElement stageResultsClose = new WebDriverWait(determineTeamsWebDriver, Duration.ofMillis(2000)).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div[3]/a")));

            for (int i = 2; i <= 9; i++) {
                String riderSurame = stageResultsClose.findElement(By.xpath("/html/body/div[2]/div[4]/div/table/tbody/tr[" + i + "]/td[1]")).getText().trim();
                riderSurame = riderSurame.split(" {2}", 2)[0].trim().split(" ", 2)[1];
                usersTeams.addRidertoUsersTeam(username, riderSurame);
            }

            stageResultsClose.click();
        }

        return usersTeams;
    }
}
