package com.lukegjpotter.tools.fantasycyclingleaguetools.controller;

import com.lukegjpotter.tools.fantasycyclingleaguetools.standings.service.FantasyCyclingLeagueStandingsService;
import com.lukegjpotter.tools.fantasycyclingleaguetools.teams.service.FantasyCyclingLeagueTeamsService;
import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.service.FantasyCyclingLeagueTransferService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FantasyCyclingLeagueRestControllerTest {

    @InjectMocks
    private FantasyCyclingLeagueRestController restController;
    @Mock
    private FantasyCyclingLeagueTransferService transferService;
    @Mock
    private FantasyCyclingLeagueStandingsService standingsService;
    @Mock
    private FantasyCyclingLeagueTeamsService teamsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTransfers() {
        Mockito.when(transferService.getTransfers()).thenReturn("Mocked Team");

        String expected = "Mocked Team";
        String actual = restController.getTransfers();

        assertEquals(expected, actual, "Mocked Transfer Service is not correct.");
    }

    @Test
    void getStandings() {
        Mockito.when(standingsService.getStandings()).thenReturn("Mocked Standings");

        String expected = "Mocked Standings";
        String actual = restController.getStandings();

        assertEquals(expected, actual, "Mocked Standings Service is not correct.");
    }

    @Test
    void getTeams() {
        Mockito.when(teamsService.getTeams()).thenReturn("Mocked Teams");

        String expected = "Mocked Teams";
        String actual = restController.getTeams();

        assertEquals(expected, actual, "Mocked Teams Service is not correct");
    }

    @Test
    void getHealth() {
        String expected = "OK";
        String actual = restController.getHealth();

        assertEquals(expected, actual, "Health Service is not correct");
    }
}