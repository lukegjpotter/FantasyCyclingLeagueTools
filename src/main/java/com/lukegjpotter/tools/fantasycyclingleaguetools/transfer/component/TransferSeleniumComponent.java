package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.component;

import com.lukegjpotter.tools.fantasycyclingleaguetools.common.CommonWebDriverOperations;
import com.lukegjpotter.tools.fantasycyclingleaguetools.common.CommonWebsiteOperations;
import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.model.UserTransfer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransferSeleniumComponent {

    @Autowired
    private CommonWebsiteOperations commonWebsiteOperations;
    @Autowired
    private CommonWebDriverOperations commonWebDriverOperations;
    @Autowired
    private StageNumberDeterminerComponent stageNumberDeterminerComponent;

    private final Logger logger = LoggerFactory.getLogger(TransferSeleniumComponent.class);

    public List<UserTransfer> getTransfers() {

        // Open website and login.
        WebDriver transfersWebDriver = commonWebDriverOperations.getWebDriverHeadless();
        commonWebsiteOperations.openRoadCcFantasyWebsite(transfersWebDriver);
        boolean isLoginSuccessful = commonWebsiteOperations.login(transfersWebDriver);
        if (!isLoginSuccessful) return new ArrayList<>();

        // View Competition
        boolean isJoinedCompetitionsAvailable = commonWebsiteOperations.selectCompetition(transfersWebDriver);
        if (!isJoinedCompetitionsAvailable) return new ArrayList<>();

        // View League
        boolean isJoinedLeaguesAvailable = commonWebsiteOperations.viewLeague(transfersWebDriver);
        if (!isJoinedLeaguesAvailable) return new ArrayList<>();

        // Determine the Latest Stage
        String[] raceNameStageNumber = stageNumberDeterminerComponent.determineLatestStage();
        String todaysStageNumber = raceNameStageNumber[0] + " " + raceNameStageNumber[1];
        logger.info("Latest Stage is {}", todaysStageNumber);

        // Open Each User
        List<UserTransfer> usersAndTransfers = viewUsersAndGetTransfers(transfersWebDriver, raceNameStageNumber);

        // Clean Up
        logger.info("Finished getting the Transfers for {}", todaysStageNumber);
        commonWebsiteOperations.logout(transfersWebDriver);
        transfersWebDriver.quit();

        return usersAndTransfers;
    }

    private List<UserTransfer> viewUsersAndGetTransfers(WebDriver transfersWebDriver, String[] raceNameStageNumber) {
        logger.info("Viewing User Profiles");
        String todaysStageNumber = raceNameStageNumber[0] + " " + raceNameStageNumber[1];
        List<UserTransfer> usersAndTransfers = new ArrayList<>();

        // Read the League Table
        List<String> usernames = new ArrayList<>();
        List<WebElement> standingsTableRows = transfersWebDriver.findElement(By.className("leagues")).findElements(By.tagName("tr"));
        standingsTableRows.remove(0); // Remove the Table Header Row.
        for (WebElement standingsTableRow : standingsTableRows) {
            List<WebElement> standingsTableFields = standingsTableRow.findElements(By.tagName("td"));
            usernames.add(commonWebsiteOperations.getUsernameFromLeagueTable(standingsTableFields));
        }

        int numberOfAnchorTags = transfersWebDriver.findElement(By.className("leagues")).findElements(By.tagName("a")).size();
        // User every second entry, as the other ones are a score link.
        for (int i = 0; i < numberOfAnchorTags; i += 2) {
            String username = usernames.get(i / 2);

            logger.info("Viewing User: {}", username);
            UserTransfer userTransfer = new UserTransfer(username);

            // Avoids a Stale Element issue.
            transfersWebDriver.findElement(By.className("leagues")).findElements(By.tagName("a")).get(i).click();

            // Expand Transfers
            transfersWebDriver.findElement(By.className("usertransfers")).findElement(By.tagName("a")).click();
            List<WebElement> transferTableRows = transfersWebDriver.findElement(By.className("usertransfers")).findElement(By.className("leagues")).findElements(By.tagName("tr"));

            // Remove the Table Headers.
            transferTableRows.remove(0);

            int usedTransfers = 0;
            String raceName = raceNameStageNumber[0];
            for (WebElement transferTableRow : transferTableRows) {
                List<WebElement> transferFields = transferTableRow.findElements(By.tagName("td"));
                String stage = transferFields.get(1).getText().trim().toLowerCase();

                if (stage.startsWith(raceName)) usedTransfers++;
                else break;
            }
            userTransfer.setUsedTransfers(usedTransfers);

            for (WebElement transferTableRow : transferTableRows) {
                List<WebElement> transferFields = transferTableRow.findElements(By.tagName("td"));
                String stage = transferFields.get(1).getText().trim();
                if (stage.equalsIgnoreCase(todaysStageNumber)) {
                    String riderOut = transferFields.get(4).getText().trim().split(" ", 2)[1];
                    String riderIn = transferFields.get(3).getText().trim().split(" ", 2)[1];
                    userTransfer.addTransfer(riderOut + " -> " + riderIn);
                } else { // We've finished getting the transfers for the Stage.
                    /* Reverse the order of the transfers, as it's providing an incorrect result to the /teams endpoint.
                     * This is in the case of Mas -> Affini and Affini -> Ayuso, in the case that a rider abandons after
                     * the initial transfer has been made. */
                    userTransfer.reverseTransferOrder();
                    usersAndTransfers.add(userTransfer);
                    break;
                }
            }
            transfersWebDriver.navigate().back();
        }
        return usersAndTransfers;
    }
}
