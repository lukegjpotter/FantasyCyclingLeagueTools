package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
public class TransferSeleniumComponent {

    @Autowired
    Environment env;

    Logger logger = LoggerFactory.getLogger(TransferSeleniumComponent.class);

    public String getTransfers() {

        ChromeOptions options = new ChromeOptions();
        WebDriver transfersWebDriver = new ChromeDriver(options);

        // Login using Environmental Variables: ROADCC_USERNAME & ROADCC_PASSWORD
        logger.info("Logging into Road.cc Fantasy with Username: " + env.getProperty("ROADCC_USERNAME"));
        transfersWebDriver.get("https://fantasy.road.cc/home");
        transfersWebDriver.findElement(By.name("user")).sendKeys(env.getProperty("ROADCC_USERNAME"));
        transfersWebDriver.findElement(By.name("pass")).sendKeys(env.getProperty("ROADCC_PASSWORD"));
        transfersWebDriver.findElement(By.className("login-submit")).click();

        // Select Competition - Giro, Tour, Vuelta.
        logger.info("Selecting League");

        WebElement competitionWebElement = transfersWebDriver.findElements(By.className("joinedcomp")).get(0);
        boolean isRaceOver = competitionWebElement.findElement(By.className("ribbon-grey")).getText().trim().equals("ENDED");
        competitionWebElement.findElement(By.className("joinbutton")).click();

        // Determine the Latest Stage
        logger.info("Determining Latest Stage");
        /* The touchcarousel-item element will return the three displayed stages.
         * List size will be 21, but the other 18 entries will be blank.
         * This will not be an issue whilst the race is ongoing, as it will load the previous stage and the next two.
         * So all the content we want will be loaded.
         * But if the competition is finished, then we'll need to press the button to scroll the carousel. */
        List<WebElement> stagesCarouselList = transfersWebDriver.findElements(By.className("touchcarousel-item"));

        if (isRaceOver) {
            // Click the right arrow the stagesCarouselList.size, number of times to get to the latest stages.
            WebElement rightButton = transfersWebDriver.findElement(By.cssSelector(".arrow-holder.right"));
            for (int i = 0; i < stagesCarouselList.size(); i++) {
                rightButton.click();
            }
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

        todaysStageNumber = todaysStageNumber.toLowerCase();
        logger.info("Latest Stage is " + todaysStageNumber);

        // View League
        logger.info("Viewing League");
        transfersWebDriver.findElement(By.id("leaguelist-current")).findElement(By.partialLinkText("Wednesday Warrior")).click();

        // Loop - Open Each User
        logger.info("Viewing User Profiles");
        // TODO this is weak, and needs to accuratly target the user names, as opposed to the scores.
        List<WebElement> usersProfiles = transfersWebDriver.findElement(By.className("leagues")).findElements(By.tagName("a"));
        StringBuilder output = new StringBuilder("Transfers: Out -> In\n");
        for (WebElement userProfile : usersProfiles) {
            userProfile.click();
            String username = transfersWebDriver.findElement(By.className("gamewindow-title")).getText().trim().split(":")[1].trim();
            logger.info("Viewing User: " + username);
            output.append(username).append("\n");

            // Expand Transfers
            transfersWebDriver.findElement(By.className("usertransfers")).findElement(By.tagName("a")).click();
            List<WebElement> transferTableRows = transfersWebDriver.findElement(By.className("usertransfers")).findElement(By.className("leagues")).findElements(By.tagName("tr"));

            // Remove the Table Headers.
            transferTableRows.remove(0);

            for (WebElement transferTableRow : transferTableRows) {
                List<WebElement> transferFields = transferTableRow.findElements(By.tagName("td"));
                String stage = transferFields.get(1).getText().trim();
                if (stage.contains(todaysStageNumber)) {
                    String riderOut = transferFields.get(4).getText().trim();
                    String riderIn = transferFields.get(3).getText().trim();
                    output.append(riderOut).append(" -> ").append(riderIn).append("\n");
                } else {
                    // End Loop, as we're no longer on the Today's Stage.
                    output.append("\n");
                    break;
                }
            }
            break; //todo remove this.
        }

        // Clean Up
        transfersWebDriver.quit();

        return output.toString();
    }
}
