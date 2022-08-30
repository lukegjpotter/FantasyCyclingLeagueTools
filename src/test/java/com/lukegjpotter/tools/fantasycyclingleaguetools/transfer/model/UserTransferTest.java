package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserTransferTest {

    @Test
    void testToString_oneTransfer() {
        UserTransfer userTransfer = new UserTransfer("luke");
        userTransfer.addTransfer("tom -> harry");

        String expected = "luke (1)<br>tom -> harry<br>";
        String actual = userTransfer.toString();

        assertEquals(expected, actual, "Actual was: " + actual + ".");
    }

    @Test
    void testToString_zeroTransfers() {
        UserTransfer userTransfer = new UserTransfer("luke");

        String expected = "luke (0)<br>";
        String actual = userTransfer.toString();

        assertEquals(expected, actual, "Actual was: " + actual + ".");
    }

    @Test
    void testToString_twoTransfer() {
        UserTransfer userTransfer = new UserTransfer("luke");
        userTransfer.addTransfer("tom -> harry");
        userTransfer.addTransfer("tom -> harry");

        String expected = "luke (2)<br>tom -> harry<br>tom -> harry<br>";
        String actual = userTransfer.toString();

        assertEquals(expected, actual, "Actual was: " + actual + ".");
    }

    @Test
    void reverseTransferOrder() {
        UserTransfer userTransfer = new UserTransfer("luke");
        userTransfer.addTransfer("abandon -> replacement");
        userTransfer.addTransfer("random -> transfer");
        userTransfer.addTransfer("original -> abandon");
        userTransfer.reverseTransferOrder();

        String expected = "luke (3)<br>" +
                "original -> abandon<br>" +
                "random -> transfer<br>" +
                "abandon -> replacement<br>";
        String actual = userTransfer.toString();

        assertEquals(expected, actual, "Actual was: " + actual + ".");
    }
}