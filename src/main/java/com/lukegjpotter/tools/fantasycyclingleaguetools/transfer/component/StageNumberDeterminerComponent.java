package com.lukegjpotter.tools.fantasycyclingleaguetools.transfer.component;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

@Component
public class StageNumberDeterminerComponent {

    private final String pathToCsvFile;
    private final String datePattern;

    private int monthNumber;
    private String todaysDate;

    public StageNumberDeterminerComponent() {
        pathToCsvFile = "src/main/resources/GranTourDatesAndStages.csv";
        datePattern = "dd/MM/yyyy";
        monthNumber = 0;
        todaysDate = "";
    }

    /**
     * Determines the latest Stage.
     *
     * @return String Array [Race Name, Stage Number]
     */
    public String[] determineLatestStage() {
        int linesToSkip;
        if (monthNumber == 0) monthNumber = LocalDate.now().getMonthValue();
        String[] raceNameStageNumber = new String[]{"", ""};

        // Cut down on linear search time through the CSV file, by skipping old Races.
        if (monthNumber == 5 || monthNumber == 6) linesToSkip = 1; // Giro is in May, and maybe June.
        else if (monthNumber == 7) linesToSkip = 24; // Tour is in July.
        else if (monthNumber <= 10) linesToSkip = 47; // Vuelta spans August and September.
        else return raceNameStageNumber;

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(pathToCsvFile)).withSkipLines(linesToSkip).build()) {
            String[] lineInArray;
            if (todaysDate.equals(""))
                todaysDate = new SimpleDateFormat(datePattern, new Locale("en", "UK")).format(new Date());

            while ((lineInArray = csvReader.readNext()) != null) {
                if (lineInArray[0].equals(todaysDate)) {
                    raceNameStageNumber[0] = lineInArray[2];
                    raceNameStageNumber[1] = lineInArray[1].toLowerCase();
                    break;
                }
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }

        return raceNameStageNumber;
    }

    /**
     * This is for Unit Testing.
     *
     * @param monthNumber Integer - Number of the Month, January is 1
     */
    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    /**
     * This is for Unit Testing.
     *
     * @param todaysDate String - Format of "31/12/2023"
     */
    public void setTodaysDate(String todaysDate) {
        this.todaysDate = todaysDate;
    }
}
