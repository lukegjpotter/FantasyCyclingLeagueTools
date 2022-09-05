package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.service;

import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.component.TransferSeleniumComponent;
import com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.model.UserTransfer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FantasyCyclingLeagueTransferServiceTest {

    @InjectMocks
    private FantasyCyclingLeagueTransferService transferService;
    @Mock
    private TransferSeleniumComponent transferSeleniumComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTransfers_zeroTransfers() {
        Mockito.when(transferSeleniumComponent.getTransfers()).thenReturn(new ArrayList<>());

        String expected = "<html><head><title>Transfers</title></head><body><p>Transfers: Out -> In<br><br></p></body></html>";
        String actual = transferService.getTransfers();

        assertEquals(expected, actual, "Mocked Object not working.");
    }

    @Test
    void testGetTransfers_oneTransfer() {
        List<UserTransfer> userTransfers = new ArrayList<>();
        UserTransfer userTransfer = new UserTransfer("Johnny");
        userTransfer.addTransfer("Bob -> Ligma");
        userTransfer.setUsedTransfers(1);
        userTransfers.add(userTransfer);
        Mockito.when(transferSeleniumComponent.getTransfers()).thenReturn(userTransfers);

        String expected = "<html><head><title>Transfers</title></head><body><p>Transfers: Out -> In<br><br><strong>Johnny</strong><br>Today: 1 | Remaining: 44<br>Bob -> Ligma<br><br><br></p></body></html>";
        String actual = transferService.getTransfers();

        assertEquals(expected, actual, "Mocked Object not working.");
    }
}