package com.lukegjpotter.tools.fantasycyclingleaguetools;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Fantasy Cycling League Tools API", version = "0.0.1", description = "A suite of Spring Boot REST APIs for a Fantasy Cycling League."))
public class FantasyCyclingLeagueToolsApplication {

    private static final Logger logger = LoggerFactory.getLogger(FantasyCyclingLeagueToolsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FantasyCyclingLeagueToolsApplication.class, args);
        WebDriverManager.chromedriver().setup();

        logger.info("Application Started");
    }
}
