package de.exxcellent.challenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import de.exxcellent.challenge.CSVDataAnalyzerTest;

/**
 * JUnit4 test case for the CSV data analyzer class.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de> (original skeleton)
 * @author Nicolas Roeser <nicolas.roeser@uni-ulm.de> (challenge solution)
 */
public class CSVDataAnalyzerTest {

    /*
     * Note to reviewers: this test class is rather simple. More tests
     * (regression tests) are usually added when bugs are found.
     */

    @Test
    public void minimumSpreadTest() throws IOException {
        final String testInputData =
              "name,min,max\n"
            + "this,10,20\n"
            + "is,-8,19\n"
            + "our,67,68\n"
            + "successful,42,42\n"
            + "small,33,33\n"
            + "test,4,-32\n"
            ;

        final byte[] testInputBytes =
                testInputData.getBytes(StandardCharsets.UTF_8);

        final ByteArrayInputStream in =
                new ByteArrayInputStream(testInputBytes);

        final CSVDataAnalyzer analyzer =
                new CSVDataAnalyzer(in, "min", "max", "name");

        final String successLabel = analyzer.getMinSpreadLabel();
        assertEquals("successful", successLabel, "wrong record detected");
    }

}
