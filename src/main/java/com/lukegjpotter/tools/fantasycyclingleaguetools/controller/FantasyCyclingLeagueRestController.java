package com.lukegjpotter.tools.fantasycyclingleaguetools.controller;

import com.lukegjpotter.tools.fantasycyclingleaguetools.service.FantasyCyclingLeagueTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class FantasyCyclingLeagueRestController {

    @Autowired
    FantasyCyclingLeagueTransferService transfersService;

    @GetMapping("transfers")
    public String getTransfers() {
        return transfersService.getTransfers();
    }

    @GetMapping("standings")
    public String getStandings() {
        return "Not Implemented\n";
    }

    @GetMapping("teams")
    public String getTeams(@PathVariable int numberOfTeams) {
        return "Not Implemented\n";
    }
}
