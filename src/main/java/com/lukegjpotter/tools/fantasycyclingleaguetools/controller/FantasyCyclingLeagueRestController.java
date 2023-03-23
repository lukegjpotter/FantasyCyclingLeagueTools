package com.lukegjpotter.tools.fantasycyclingleaguetools.controller;

import com.lukegjpotter.tools.fantasycyclingleaguetools.standings.service.FantasyCyclingLeagueStandingsService;
import com.lukegjpotter.tools.fantasycyclingleaguetools.teams.service.FantasyCyclingLeagueTeamsService;
import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.service.FantasyCyclingLeagueTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class FantasyCyclingLeagueRestController {

    @Autowired
    private FantasyCyclingLeagueTransferService transfersService;
    @Autowired
    private FantasyCyclingLeagueStandingsService standingsService;
    @Autowired
    private FantasyCyclingLeagueTeamsService teamsService;

    private final Logger logger = LoggerFactory.getLogger(FantasyCyclingLeagueRestController.class);

    @GetMapping("transfers")
    public String getTransfers() {
        logger.info("Endpoint transfers called");
        return transfersService.getTransfers();
    }

    @GetMapping("standings")
    public String getStandings() {
        logger.info("Endpoint standings called");
        return standingsService.getStandings();
    }

    @GetMapping("teams")
    public String getTeams() {
        logger.info("Endpoint teams called");
        return teamsService.getTeams();
    }

    @GetMapping("health")
    public String getHealth() {
        logger.info("Endpoint health called");
        return "OK";
    }
}
