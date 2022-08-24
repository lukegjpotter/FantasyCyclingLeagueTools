package com.lukegjpotter.tools.fantasycyclingleaguetools.standings.service;

import com.lukegjpotter.tools.fantasycyclingleaguetools.standings.component.StandingSeleniumComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FantasyCyclingLeagueStandingsService {

    @Autowired
    private StandingSeleniumComponent standingSeleniumComponent;

    public String getStandings() {
        // ToDo - Refactor, use POJOs and separate the HTML Wrapping.
        return standingSeleniumComponent.getStandings();
    }
}
