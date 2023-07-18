package com.lukegjpotter.tools.fantasycyclingleaguetools.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CommonWebsiteOperationsTest {

    @Autowired
    CommonWebsiteOperations commonWebsiteOperations;

    @Test
    void getRiderNameFromTransferList_AdamYates() {
        String expected = "A. Yates";
        String actual = commonWebsiteOperations.getRiderNameFromTransferList("Adam Yates");

        assertEquals(expected, actual);
    }

    @Test
    void getRiderNameFromTransferList_MvdP() {
        String expected = "M. van der Poel";
        String actual = commonWebsiteOperations.getRiderNameFromTransferList("Mathieu van der Poel");

        assertEquals(expected, actual);
    }

    @Test
    void getRiderNameFromTransferList_WVA() {
        String expected = "W. Van Aert";
        String actual = commonWebsiteOperations.getRiderNameFromTransferList("Wout Van Aert");

        assertEquals(expected, actual);
    }

    @Test
    void getRiderNameFromTransferList_Skjelmose() {
        String expected = "M. Skjelmose";
        String actual = commonWebsiteOperations.getRiderNameFromTransferList("Mattias Skjelmose Jensen");

        assertEquals(expected, actual);
    }

    @Test
    void getRiderNameFromTransferList_TobiasJohannessen() {
        String expected = "T. Johannessen";
        String actual = commonWebsiteOperations.getRiderNameFromTransferList("Tobias Halland Johannessen");

        assertEquals(expected, actual);
    }

    @Test
    void getRiderNameFromTransferList_GeogheganHart() {
        String expected = "T. Geoghegan-Hart";
        String actual = commonWebsiteOperations.getRiderNameFromTransferList("Tao Geoghegan-Hart");

        assertEquals(expected, actual);
    }

    @Test
    void getRiderNameFromTransferList_DaniMartinez() {
        String expected = "D. Martinez";
        String actual = commonWebsiteOperations.getRiderNameFromTransferList("Daniel Felipe Martinez");

        assertEquals(expected, actual);
    }

}