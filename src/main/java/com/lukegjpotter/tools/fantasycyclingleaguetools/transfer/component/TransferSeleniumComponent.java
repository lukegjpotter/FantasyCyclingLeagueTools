package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.component;

import io.github.bonigarcia.wdm.WebDriverManager;
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

        WebDriverManager.chromedriver().setup();
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
        transfersWebDriver.findElements(By.className("joinedcomp")).get(0).findElement(By.className("joinbutton")).click();

        // Determine the Latest Stage
        logger.info("Determining Latest Stage");
        List<WebElement> stagesCarouselList = transfersWebDriver.findElements(By.className("touchcarousel-item"));

        String todaysDateFormatted = new SimpleDateFormat("EEEEE dd MMMMM", new Locale("en", "UK")).format(new Date());
        String todaysStageNumber = "";

        for (WebElement stageElement : stagesCarouselList) {
            String dateAndDistance = stageElement.findElement(By.className("scr-details")).getText().trim().split("\n")[0];
            if (dateAndDistance.equals(todaysDateFormatted)) {
                todaysStageNumber = stageElement.findElement(By.className("scr-stagemarker")).getText().trim();
                break;
            }
        }

        if (todaysStageNumber.isEmpty()) {
            todaysStageNumber = "Stage 21";
        }
        logger.info("Latest Stage is " + todaysStageNumber);

        // View League
        logger.info("Viewing League");
        transfersWebDriver.findElement(By.id("leaguelist-current")).findElement(By.partialLinkText("Wednesday Warrior")).click();

        // Loop - Open Each User
        logger.info("Viewing User Profiles");
        List<WebElement> usersProfiles = transfersWebDriver.findElement(By.className("leagues")).findElements(By.tagName("a"));
        StringBuilder output = new StringBuilder("Transfers: Out -> In\n");

        for (WebElement userProfile : usersProfiles) {
            userProfile.click();
            String username = transfersWebDriver.findElement(By.className("gamewindow-title")).getText().trim().split(":")[1].trim();
            logger.info("Viewing User: " + username);
            output.append(username).append("\n");

            // Expand Transfers
            transfersWebDriver.findElement(By.className("usertransfers")).findElement(By.tagName("a")).click();
            List<WebElement> transferTableRows = transfersWebDriver.findElement(By.className("leagues")).findElements(By.tagName("tr"));
            transferTableRows.remove(0); // Remove the Table Headers.

            for (WebElement transferTableRow : transferTableRows) {
                List<WebElement> transferFields = transferTableRow.findElements(By.tagName("td"));
                String stage = transferFields.get(1).getText().trim();

                if (stage.contains(todaysStageNumber)) {
                    String riderOut = transferFields.get(3).getText().trim();
                    String riderIn = transferFields.get(2).getText().trim();
                    output.append(riderOut).append(" -> ").append(riderIn).append("\n");
                } else {
                    // End Loop, as we're no longer on the Today's Stage.
                    output.append("\n");
                    break;
                }
            }
        }

        // Clean Up
        transfersWebDriver.quit();

        return output.toString().trim();
    }
}
