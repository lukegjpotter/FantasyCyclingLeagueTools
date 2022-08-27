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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class TransferSeleniumComponent {

    @Autowired
    private CommonWebsiteOperations commonWebsiteOperations;
    @Autowired
    private CommonWebDriverOperations commonWebDriverOperations;

    private final Logger logger = LoggerFactory.getLogger(TransferSeleniumComponent.class);

    public String getTransfers() {

        // Open website and login.
        WebDriver transfersWebDriver = commonWebDriverOperations.getWebDriverHeadless();
        commonWebsiteOperations.openRoadCcFantasyWebsite(transfersWebDriver);
        commonWebsiteOperations.login(transfersWebDriver);

        // Select Competition - Giro, Tour, Vuelta.
        boolean isRaceOver = commonWebsiteOperations.selectCompetition(transfersWebDriver);

        // Determine the Latest Stage
        String todaysStageNumber = determineLatestStage(transfersWebDriver, isRaceOver);
        logger.info("Latest Stage is {}", todaysStageNumber);

        // View League
        commonWebsiteOperations.viewLeague(transfersWebDriver);

        // Open Each User
        List<UserTransfer> usersAndTransfers = viewUsersAndGetTransfers(transfersWebDriver, todaysStageNumber);

        // Clean Up
        logger.info("Finished getting the Transfers for {}", todaysStageNumber);
        commonWebsiteOperations.logout(transfersWebDriver);
        transfersWebDriver.quit();

        // Prepare Output
        StringBuilder output = new StringBuilder("<html><head><title>Transfers</title></head><body><p>Transfers: Out -> In<br><br>");
        usersAndTransfers.forEach(userTransfer -> {
            if (userTransfer.hasTransfers()) output.append(userTransfer).append("<br><br>");
        });
        output.append("</p></body></html>");

        return output.toString();
    }

    private String determineLatestStage(WebDriver transfersWebDriver, boolean isRaceOver) {
        logger.info("Determining Latest Stage");

        /* The touchcarousel-item element will return the three displayed stages.
         * List size will be 21, but the other 18 entries will be blank.
         * This will not be an issue whilst the race is ongoing, as it will load the previous stage and the next two.
         * So all the content we want will be loaded.
         * But if the competition is finished, then we'll need to press the button to scroll the carousel. */
        List<WebElement> stagesCarouselList = transfersWebDriver.findElements(By.className("touchcarousel-item"));

        // FixMe - This will break for stage 21, as I will click it back one and 21 will disspear.
        if (isRaceOver) {
            // Click the right arrow the stagesCarouselList.size, number of times to get to the latest stages.
            WebElement rightButton = transfersWebDriver.findElement(By.cssSelector(".arrow-holder.right"));
            for (int i = 0; i < stagesCarouselList.size(); i++) {
                rightButton.click();
            }
        } else {
            // Go back by one stage, then read the list.
            transfersWebDriver.findElement(By.cssSelector(".arrow-holder.left")).click();
        }

        stagesCarouselList = transfersWebDriver.findElements(By.className("touchcarousel-item"));

        String todaysDate = new SimpleDateFormat("EEEEE dd MMMMM", new Locale("en", "UK")).format(new Date());
        String todaysStageNumber = "";

        for (WebElement stageElement : stagesCarouselList) {
            String stageDate = stageElement.findElement(By.className("scr-details")).getText().trim().split("\n")[0];
            todaysStageNumber = stageElement.findElement(By.className("scr-stagemarker")).getText().trim();

            if (stageDate.equals(todaysDate)) {
                break;
            }
        }

        String raceName = transfersWebDriver.findElement(By.id("compnav")).findElement(By.className("content")).findElement(By.tagName("H1")).getText().split("-")[0].trim().toLowerCase();


        return raceName + " " + todaysStageNumber.toLowerCase();
    }

    private List<UserTransfer> viewUsersAndGetTransfers(WebDriver transfersWebDriver, String todaysStageNumber) {
        logger.info("Viewing User Profiles");
        List<UserTransfer> usersAndTransfers = new ArrayList<>();

        int numberOfAnchorTags = transfersWebDriver.findElement(By.className("leagues")).findElements(By.tagName("a")).size();
        // User every second entry, as the other ones are a score link.
        for (int i = 0; i < numberOfAnchorTags; i += 2) {
            // Avoids a Stale Element issue.
            transfersWebDriver.findElement(By.className("leagues")).findElements(By.tagName("a")).get(i).click();

            String username = transfersWebDriver.findElement(By.className("gamewindow-title")).getText().trim().split(":")[1].trim();
            logger.info("Viewing User: {}", username);
            UserTransfer userTransfer = new UserTransfer(username);

            // Expand Transfers
            transfersWebDriver.findElement(By.className("usertransfers")).findElement(By.tagName("a")).click();
            List<WebElement> transferTableRows = transfersWebDriver.findElement(By.className("usertransfers")).findElement(By.className("leagues")).findElements(By.tagName("tr"));

            // Remove the Table Headers.
            transferTableRows.remove(0);

            for (WebElement transferTableRow : transferTableRows) {
                List<WebElement> transferFields = transferTableRow.findElements(By.tagName("td"));
                String stage = transferFields.get(1).getText().trim();
                if (stage.equalsIgnoreCase(todaysStageNumber)) {
                    String riderOut = transferFields.get(4).getText().trim().split(" ", 2)[1];
                    String riderIn = transferFields.get(3).getText().trim().split(" ", 2)[1];
                    userTransfer.addTransfer(riderOut + " -> " + riderIn);
                } else {
                    usersAndTransfers.add(userTransfer);
                    break;
                }
            }
            transfersWebDriver.navigate().back();
        }
        return usersAndTransfers;
    }
}
