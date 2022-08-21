package com.lukegjpotter.tools.fantasycyclingleaguetools.teams.service;

import com.lukegjpotter.tools.fantasycyclingleaguetools.teams.component.TeamSeleniumComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FantasyCyclingLeagueTeamsServiceTest {

    @InjectMocks
    private FantasyCyclingLeagueTeamsService teamsService;
    @Mock
    private TeamSeleniumComponent teamSeleniumComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTeams() {
        Mockito.when(teamSeleniumComponent.getTeams()).thenReturn("Mocked Teams");

        String expected = "Mocked Teams";
        String actual = teamsService.getTeams();

        assertEquals(expected, actual, "Mocked Object not working.");
    }
}