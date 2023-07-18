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
    void formatRiderName_AdamYates() {
        String expected = "A. Yates";
        String actual = commonWebsiteOperations.formatRiderName("Adam Yates");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_MvdP() {
        String expected = "M. van der Poel";
        String actual = commonWebsiteOperations.formatRiderName("Mathieu van der Poel");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_WVA() {
        String expected = "W. Van Aert";
        String actual = commonWebsiteOperations.formatRiderName("Wout Van Aert");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_Skjelmose() {
        String expected = "M. Skjelmose";
        String actual = commonWebsiteOperations.formatRiderName("Mattias Skjelmose Jensen");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_TobiasJohannessen() {
        String expected = "T. Johannessen";
        String actual = commonWebsiteOperations.formatRiderName("Tobias Halland Johannessen");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_GeogheganHart() {
        String expected = "T. Geoghegan-Hart";
        String actual = commonWebsiteOperations.formatRiderName("Tao Geoghegan-Hart");

        assertEquals(expected, actual);
    }

    @Test
    void getRiderNameFromTransferList_DaniMartinez() {
        String expected = "D. Martinez";
        String actual = commonWebsiteOperations.formatRiderName("Daniel Felipe Martinez");

        assertEquals(expected, actual);
    }

}