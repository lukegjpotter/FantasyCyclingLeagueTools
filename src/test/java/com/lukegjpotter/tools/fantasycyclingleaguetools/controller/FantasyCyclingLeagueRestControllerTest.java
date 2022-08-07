package com.lukegjpotter.tools.fantasycyclingleaguetools.controller;

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
    FantasyCyclingLeagueRestController restController;
    @Mock
    FantasyCyclingLeagueTransferService transferService;

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
        String expected = "Not Implemented\n";
        String actual = restController.getStandings();

        assertEquals(expected, actual, "Class is implemented.");
    }

    @Test
    void getTeams() {
        String expected = "Not Implemented\n";
        String actual = restController.getTeams(0);

        assertEquals(expected, actual, "Class is implemented.");
    }
}