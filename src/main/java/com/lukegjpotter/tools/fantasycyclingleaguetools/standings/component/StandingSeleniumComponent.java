package com.lukegjpotter.tools.fantasycyclingleaguetools.standings.component;

import com.lukegjpotter.tools.fantasycyclingleaguetools.common.CommonWebDriverOperations;
import com.lukegjpotter.tools.fantasycyclingleaguetools.common.CommonWebsiteOperations;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        StringBuilder standings = new StringBuilder();
        // TODO Implement This.
        standings.append("Not Implemented\n");

        // Cleanup
        commonWebsiteOperations.logout(standingsWebDriver);
        standingsWebDriver.quit();
        logger.info("Finished Getting Standings");

        return standings.toString();
    }
}
