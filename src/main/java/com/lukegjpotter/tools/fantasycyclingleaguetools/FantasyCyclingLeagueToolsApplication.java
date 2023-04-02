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
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // Chromium is only available on Alpine Linux Package Manager.
                String isFantasyCyclingToolsOnDocker = "false";

                // TODO: Use Optional here.
                if (env.getProperty("IS_FANTASY_CYCLING_TOOLS_ON_DOCKER") != null)
                    isFantasyCyclingToolsOnDocker = env.getProperty("IS_FANTASY_CYCLING_TOOLS_ON_DOCKER");

                if (isFantasyCyclingToolsOnDocker.equalsIgnoreCase("true")) {
                    logger.info("Using Chromium, as we're running on Docker");
                    WebDriverManager.chromiumdriver().setup();
                } else {
                    logger.info("Using Chrome, as we're running on Local");
                    WebDriverManager.chromedriver().setup();
                }
            }
        };
    }
}
