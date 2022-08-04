package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.service;

import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.component.TransferSeleniumComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FantasyCyclingLeagueTransferServiceTest {

    @InjectMocks
    FantasyCyclingLeagueTransferService transferService;
    @Mock
    TransferSeleniumComponent transferSeleniumComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTransfers() {
        Mockito.when(transferSeleniumComponent.getTransfers()).thenReturn("Mocked Transfers");

        String expected = "Mocked Transfers";
        String actual = transferService.getTransfers();

        assertEquals(expected, actual, "Mocked Object not working.");
    }
}