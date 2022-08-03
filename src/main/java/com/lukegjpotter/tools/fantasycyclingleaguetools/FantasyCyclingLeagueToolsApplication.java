package com.lukegjpotter.tools.fantasycyclingleaguetools;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Fantasy Cycling League Tools API", version = "0.0.1", description = "A suite of Spring Boot REST APIs for a Fantasy Cycling League."))
public class FantasyCyclingLeagueToolsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FantasyCyclingLeagueToolsApplication.class, args);
    }

}
