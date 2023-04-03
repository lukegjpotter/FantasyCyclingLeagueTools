package com.lukegjpotter.tools.fantasycyclingleaguetools;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import static java.util.Optional.ofNullable;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Fantasy Cycling League Tools API", version = "0.0.1", description = "A suite of Spring Boot REST APIs for a Fantasy Cycling League."))
public class FantasyCyclingLeagueToolsApplication {

    private static final Logger logger = LoggerFactory.getLogger(FantasyCyclingLeagueToolsApplication.class);

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(FantasyCyclingLeagueToolsApplication.class, args);

        logger.info("Application Started");
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            checkEnvironmentVariablesAreSet();
            decideBetweenChromeAndChromiumWebDriver();
        };
    }

    private void decideBetweenChromeAndChromiumWebDriver() {
        /* Chromium is only available on Alpine Linux Package Manager.
         * Chrome is not supported on Alpine Image. */
        boolean isFantasyCyclingToolsOnDocker =
                Boolean.parseBoolean(ofNullable(
                        env.getProperty("IS_FANTASY_CYCLING_TOOLS_ON_DOCKER")).orElse("false"));

        if (isFantasyCyclingToolsOnDocker) {
            logger.info("Using Chromium on Docker because of Alpine.");
            WebDriverManager.chromiumdriver().setup();
        } else {
            logger.info("Using Chrome on supported Operating System.");
            WebDriverManager.chromedriver().setup();
        }
    }

    private void checkEnvironmentVariablesAreSet() {
        logger.info("Checking Environment Variables");

        // Not a big deal if not specified.
        logger.info("IS_FANTASY_CYCLING_TOOLS_ON_DOCKER: {}",
                ofNullable(env.getProperty("IS_FANTASY_CYCLING_TOOLS_ON_DOCKER")).orElse("false"));

        // Big deal if not specified.
        String roadccUsername = ofNullable(env.getProperty("ROADCC_USERNAME")).orElse("");
        if (roadccUsername.isBlank()) logger.error("ROADCC_USERNAME is not set, please Follow ReadMe instructions.");
        else logger.info("ROADCC_USERNAME: {}", roadccUsername);

        if (ofNullable(env.getProperty("ROADCC_PASSWORD")).orElse("").isBlank())
            logger.error("ROADCC_PASSWORD is not set, please Follow ReadMe instructions.");
        else logger.info("ROADCC_PASSWORD: ******");

        String roadccLeagueName = ofNullable(env.getProperty("ROADCC_LEAGUE_NAME")).orElse("");
        if (roadccLeagueName.isBlank())
            logger.error("ROADCC_LEAGUE_NAME is not set, please Follow ReadMe instructions.");
        else logger.info("ROADCC_LEAGUE_NAME: {}", roadccLeagueName);

    }
}
