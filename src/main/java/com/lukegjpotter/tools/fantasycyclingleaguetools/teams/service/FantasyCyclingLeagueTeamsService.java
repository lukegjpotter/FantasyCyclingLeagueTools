package com.lukegjpotter.tools.fantasycyclingleaguetools.teams.service;

import com.lukegjpotter.tools.fantasycyclingleaguetools.teams.component.TeamSeleniumComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FantasyCyclingLeagueTeamsService {

    @Autowired
    private TeamSeleniumComponent teamSeleniumComponent;

    public String getTeams() {
        // ToDo - Refactor, use POJOs and separate the HTML Wrapping.
        return teamSeleniumComponent.getTeams();
    }
}
