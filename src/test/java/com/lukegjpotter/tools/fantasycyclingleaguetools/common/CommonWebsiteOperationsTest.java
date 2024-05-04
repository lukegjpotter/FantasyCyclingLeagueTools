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
        String expected = "MVDP";
        String actual = commonWebsiteOperations.formatRiderName("Mathieu van der Poel");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_WVA() {
        String expected = "WVA";
        String actual = commonWebsiteOperations.formatRiderName("Wout Van Aert");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_Skjelmose() {
        String expected = "Skjelmose";
        String actual = commonWebsiteOperations.formatRiderName("Mattias Skjelmose Jensen");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_TobiasJohannessen() {
        String expected = "Johannessen";
        String actual = commonWebsiteOperations.formatRiderName("Tobias Halland Johannessen");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_GeogheganHart() {
        String expected = "Geoghegan-Hart";
        String actual = commonWebsiteOperations.formatRiderName("Tao Geoghegan-Hart");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_DaniMartinez() {
        String expected = "Martinez";
        String actual = commonWebsiteOperations.formatRiderName("Daniel Felipe Martinez");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_DavidDeLaCruz() {
        String expected = "De La Cruz";
        String actual = commonWebsiteOperations.formatRiderName("David De La Cruz");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_MarijnVanDenBerg() {
        String expected = "M. van den Berg";
        String actual = commonWebsiteOperations.formatRiderName("Marijn van den Berg");

        assertEquals(expected, actual);
    }

    @Test
    void formatRiderName_AurelienParetPeintre() {
        String expected = "A. Paret-Peintre";
        String actual = commonWebsiteOperations.formatRiderName("Aurélien Paret-Peintre");

        assertEquals(expected, actual);
    }
}