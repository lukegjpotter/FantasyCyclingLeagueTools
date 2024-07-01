package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.component;

import com.lukegjpotter.tools.fantasycyclingleaguetools.common.CommonWebDriverOperations;
import com.lukegjpotter.tools.fantasycyclingleaguetools.common.CommonWebsiteOperations;
import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.model.UserTransfer;
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
        // Remove the Table Header Row.
        standingsTableRows.remove(0);
        // Get Usernames from League Table.
        for (WebElement standingsTableRow : standingsTableRows) {
            List<WebElement> standingsTableFields = standingsTableRow.findElements(By.tagName("td"));
            usernames.add(commonWebsiteOperations.getUsernameFromLeagueTable(standingsTableFields));
        }

        int numberOfAnchorTags = transfersWebDriver.findElement(By.className("leagues")).findElements(By.tagName("a")).size();
        // User every second entry, as the other ones are a score link.
        for (int anchorTagsIndex = 0; anchorTagsIndex < numberOfAnchorTags; anchorTagsIndex += 2) {
            String username = usernames.get(anchorTagsIndex / 2);

            logger.info("Viewing User: {}", username);
            UserTransfer userTransfer = new UserTransfer(username);

            // Avoids a Stale Element issue.
            transfersWebDriver.findElement(By.className("leagues")).findElements(By.tagName("a")).get(anchorTagsIndex).click();

            // Expand Transfers and read Transfers Table.
            transfersWebDriver.findElement(By.className("usertransfers")).findElement(By.tagName("a")).click();
            WebDriverWait transferTableWait = new WebDriverWait(transfersWebDriver, Duration.ofSeconds(10));
            By xpathUserTransfersLeaguesTable = By.xpath("//div[@class=\"usertransfers\"]//table[@class=\"leagues\"]");
            WebElement transfersTableWebElement = transferTableWait.until(ExpectedConditions.visibilityOfElementLocated(xpathUserTransfersLeaguesTable));
            List<WebElement> transferTableRows = transfersTableWebElement.findElements(By.tagName("tr"));

            // Remove the Table Headers.
            transferTableRows.remove(0);

            // FixMe: Transfers are being Flakey.
            
            /* Determine Total Transfers Used. This loop, rather than just the table.size(), is used, in the case that
             * there might be transfers related to another competition in the list. */
            int totalUsedTransfers = 0;
            String raceName = raceNameStageNumber[0];
            logger.trace("User: {}, Total Tx Used Table Size: {}", username, transferTableRows.size());
            for (WebElement transferTableRow : transferTableRows) {
                List<WebElement> transferFields = transferTableRow.findElements(By.tagName("td"));
                String stage = transferFields.get(1).getText().trim();
                logger.trace("User: {}, Stage: {}, Race Name: {}", username, stage, raceName);

                if (stage.startsWith(raceName)) totalUsedTransfers++;
                else break;
            }
            logger.trace("User: {}, Total Tx Used: {}", username, totalUsedTransfers);
            userTransfer.setTotalUsedTransfers(totalUsedTransfers);

            // Determine the actual rider for rider Transfers.
            for (WebElement transferTableRow : transferTableRows) {
                List<WebElement> transferFields = transferTableRow.findElements(By.tagName("td"));
                String stage = transferFields.get(1).getText().trim();

                if (stage.equalsIgnoreCase(todaysStageNumber) || stage.equalsIgnoreCase(todaysStageNumber + ".")) {
                    String riderOut = commonWebsiteOperations.formatRiderName(transferFields.get(4).getText().trim());
                    String riderIn = commonWebsiteOperations.formatRiderName(transferFields.get(3).getText().trim());
                    String transferAsText = riderOut + " -> " + riderIn;
                    logger.trace("User: {}. Tx: {}", username, transferAsText);
                    userTransfer.addTransfer(transferAsText);
                } else { // We've finished getting the transfers for today's Stage.
                    logger.trace("User: {}. End of Stage Transfers.", username);
                    break;
                }
            }
            /* Reverse the order of the transfers, as it's providing an incorrect result to the /teams endpoint.
             * This is in the case of Mas -> Affini and Affini -> Ayuso, in the case that a rider abandons after
             * the initial transfer has been made. */
            userTransfer.reverseTransferOrder();
            usersAndTransfers.add(userTransfer);

            transfersWebDriver.navigate().back();
        }
        return usersAndTransfers;
    }
}
