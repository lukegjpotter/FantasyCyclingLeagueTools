package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StageNumberDeterminerComponentTest {

    @Autowired
    private StageNumberDeterminerComponent stageNumberDeterminerComponent;

    @BeforeEach
    public void setup() {
        stageNumberDeterminerComponent.setPathToCsvFile("src/test/resources/GranTourDatesAndStages2023Test.csv");
    }

    @Test
    void determineLatestStage_03March() {
        stageNumberDeterminerComponent.setMonthNumber(3);
        stageNumberDeterminerComponent.setTodaysDate("22/03/2023");

        String[] expected = new String[]{"", ""};
        String[] actual = stageNumberDeterminerComponent.determineLatestStage();

        assertEquals(expected[0], actual[0], "Race Name is Incorrect");
        assertEquals(expected[1], actual[1], "Stage Name is Incorrect");
    }

    @Test
    void determineLatestStage_05Giro() {
        stageNumberDeterminerComponent.setMonthNumber(5);
        stageNumberDeterminerComponent.setTodaysDate("11/05/2023");

        String[] expected = new String[]{"Giro d'Italia", "stage 6"};
        String[] actual = stageNumberDeterminerComponent.determineLatestStage();

        assertEquals(expected[0], actual[0], "Race Name is Incorrect");
        assertEquals(expected[1], actual[1], "Stage Name is Incorrect");
    }

    @Test
    void determineLatestStage_07Tour() {
        stageNumberDeterminerComponent.setMonthNumber(7);
        stageNumberDeterminerComponent.setTodaysDate("18/07/2023");

        String[] expected = new String[]{"Tour de France", "stage 16"};
        String[] actual = stageNumberDeterminerComponent.determineLatestStage();

        assertEquals(expected[0], actual[0], "Race Name is Incorrect");
        assertEquals(expected[1], actual[1], "Stage Name is Incorrect");
    }

    @Test
    void determineLatestStage_08Vuelta() {
        stageNumberDeterminerComponent.setMonthNumber(9);
        stageNumberDeterminerComponent.setTodaysDate("17/09/2023");

        String[] expected = new String[]{"Vuelta a Espana", "stage 21"};
        String[] actual = stageNumberDeterminerComponent.determineLatestStage();

        assertEquals(expected[0], actual[0], "Race Name is Incorrect");
        assertEquals(expected[1], actual[1], "Stage Name is Incorrect");
    }

    @Test
    void determineLatestStage_11November() {
        stageNumberDeterminerComponent.setMonthNumber(11);
        stageNumberDeterminerComponent.setTodaysDate("11/11/2023");

        String[] expected = new String[]{"", ""};
        String[] actual = stageNumberDeterminerComponent.determineLatestStage();

        assertEquals(expected[0], actual[0], "Race Name is Incorrect");
        assertEquals(expected[1], actual[1], "Stage Name is Incorrect");
    }
}