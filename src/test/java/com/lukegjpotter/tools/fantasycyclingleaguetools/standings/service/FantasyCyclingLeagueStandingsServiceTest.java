package com.lukegjpotter.tools.fantasycyclingleaguetools.standings.service;

import com.lukegjpotter.tools.fantasycyclingleaguetools.standings.component.StandingSeleniumComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FantasyCyclingLeagueStandingsServiceTest {

    @InjectMocks
    private FantasyCyclingLeagueStandingsService standingsService;
    @Mock
    private StandingSeleniumComponent standingSeleniumComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getStandings() {
        Mockito.when(standingSeleniumComponent.getStandings()).thenReturn("Mocked Standings");

        String expected = "Mocked Standings";
        String actual = standingsService.getStandings();

        assertEquals(expected, actual, "Mocked Standings Service is not correct.");
    }
}