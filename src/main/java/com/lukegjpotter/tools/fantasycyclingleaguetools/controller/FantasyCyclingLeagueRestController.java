package com.lukegjpotter.tools.fantasycyclingleaguetools.controller;

import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.service.FantasyCyclingLeagueTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class FantasyCyclingLeagueRestController {

    @Autowired
    FantasyCyclingLeagueTransferService transfersService;

    private final Logger logger = LoggerFactory.getLogger(FantasyCyclingLeagueRestController.class);

    @GetMapping("transfers")
    public String getTransfers() {
        logger.info("Endpoint transfers called");
        return transfersService.getTransfers();
    }

    @GetMapping("standings")
    public String getStandings() {
        logger.info("Endpoint standings called");
        return "Not Implemented\n";
    }

    @GetMapping("teams")
    public String getTeams(@PathVariable int numberOfTeams) {
        logger.info("Endpoint teams called");
        return "Not Implemented\n";
    }
}
