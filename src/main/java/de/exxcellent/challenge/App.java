package de.exxcellent.challenge;

import java.io.InputStream;
import java.io.IOException;

/**
 * Starter for data analysis. First starts the weather, then the football
 * analysis.
 * <P>
 * This is the launcher part of the solution to the eXXcellent weather
 * programming challenge.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de> (original skeleton)
 * @author Nicolas Roeser <nicolas.roeser@uni-ulm.de> (challenge solution)
 */
/*
 * Note to reviewers: I would normally have renamed the class in order to give
 * it a more suitable name which tells more about what it does. For the
 * programming challenge, I have skipped this.
 */
public final class App {

    private static final String WEATHER_RESOURCE_NAME = "weather.csv";
    private static final String[] WEATHER_COLUMN_HEADINGS =
        { "MnT", "MxT", "Day" };

    private static final String FOOTBALL_RESOURCE_NAME = "football.csv";
    private static final String[] FOOTBALL_COLUMN_HEADINGS =
        { "Goals Allowed", "Goals", "Team" };

    /**
     * Process weather input and print output. Pass on I/O errors for handling
     * by the caller.
     *
     * @throws IOException if an I/O error occurs.
     */
    public static void processWeatherData() throws IOException {
        InputStream resIn =
                App.class.getResourceAsStream(WEATHER_RESOURCE_NAME);
        CSVDataAnalyzer weatherAnalyzer =
                new CSVDataAnalyzer(resIn,
                        WEATHER_COLUMN_HEADINGS[0],
                        WEATHER_COLUMN_HEADINGS[1],
                        WEATHER_COLUMN_HEADINGS[2]);

        String dayWithSmallestTempSpread = weatherAnalyzer.getMinSpreadLabel();

        System.out.printf("Day with smallest temperature spread : %s%n",
                dayWithSmallestTempSpread);
    }

    /**
     * Process football input and print output. Pass on I/O errors for handling
     * by the caller.
     *
     * @throws IOException if an I/O error occurs.
     */
    public static void processFootballData() throws IOException {
        InputStream resIn =
                App.class.getResourceAsStream(FOOTBALL_RESOURCE_NAME);
        CSVDataAnalyzer footballAnalyzer =
                new CSVDataAnalyzer(resIn,
                        FOOTBALL_COLUMN_HEADINGS[0],
                        FOOTBALL_COLUMN_HEADINGS[1],
                        FOOTBALL_COLUMN_HEADINGS[2]);

        String teamWithSmallestGoalSpread =
                footballAnalyzer.getMinSpreadLabel();

        System.out.printf("Team with smallest goal spread       : %s%n",
                teamWithSmallestGoalSpread);
    }

    /**
     * Main entry method of the data analysis program.
     *
     * @param args the CLI arguments passed. Ignored.
     */
    public static void main(String... args) {
        try {
            processWeatherData();
            processFootballData();
        } catch (IOException e) {
            // XXX: we normally use a Logger for this, but we try to KISS.
            System.err.println();
            e.printStackTrace();
            return;
        }
    }

}
