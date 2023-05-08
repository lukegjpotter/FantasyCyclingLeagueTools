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
        userTransfer.setTotalUsedTransfers(1);

        String expected = "<strong>luke</strong><br>" +
                "Today: 1 | Remaining: 44<br>" +
                "tom -> harry<br>";
        String actual = userTransfer.toString();

        assertEquals(expected, actual, "Actual was: " + actual + ".");
    }

    @Test
    void testToString_zeroTransfers() {
        UserTransfer userTransfer = new UserTransfer("luke");

        String expected = "<strong>luke</strong><br>" +
                "Today: 0 | Remaining: 45<br>";
        String actual = userTransfer.toString();

        assertEquals(expected, actual, "Actual was: " + actual + ".");
    }

    @Test
    void testToString_twoTransfer() {
        UserTransfer userTransfer = new UserTransfer("luke");
        userTransfer.addTransfer("tom -> harry");
        userTransfer.addTransfer("tom -> harry");
        userTransfer.setTotalUsedTransfers(2);

        String expected = "<strong>luke</strong><br>" +
                "Today: 2 | Remaining: 43<br>" +
                "tom -> harry<br>tom -> harry<br>";
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
        userTransfer.setTotalUsedTransfers(3);

        String expected = "<strong>luke</strong><br>" +
                "Today: 3 | Remaining: 42<br>" +
                "original -> abandon<br>" +
                "random -> transfer<br>" +
                "abandon -> replacement<br>";
        String actual = userTransfer.toString();

        assertEquals(expected, actual, "Actual was: " + actual + ".");
    }
}