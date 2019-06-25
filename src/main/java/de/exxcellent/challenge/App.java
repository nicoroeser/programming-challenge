package de.exxcellent.challenge;

import java.io.InputStream;
import java.io.IOException;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {

    private static final String WEATHER_RESOURCE_NAME = "weather.csv";
    private static final String[] WEATHER_COLUMN_HEADINGS =
        { "MnT", "MxT", "Day" };

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
        String teamWithSmallestGoalSpread = "A good team"; // Your goal analysis function call …
        System.out.printf("Team with smallest goal spread       : %s%n",
                teamWithSmallestGoalSpread);
    }

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
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
