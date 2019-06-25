package de.exxcellent.challenge;

import java.io.InputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Analyzer for tabular data. Takes CVS data as input.
 * <P>
 * For now, only used to extract the minimum spread (maximum minus minimum
 * column value) between two columns over all rows.
 * <P>
 * Processes the input once, and caches the result for later use by callers.
 * Fully consumes the input on reading. Closes the input stream after all input
 * has been read.
 * <P>
 * This class is not thread-safe.
 */
/*
 * Implementation notes:
 *   * extract superclass in future if other formats than CSV shall be handled;
 *   * this class is not thread-safe _on purpose_: thread-safety has not been
 *     required so far; you may use an external lock.
 */
public class CSVDataAnalyzer {

    /**
     * Where the input comes from.
     */
    private InputStream in;

    /**
     * Name of the column which holds minimum values.
     */
    private String minCol;

    /**
     * Name of the column which holds maximum values.
     */
    private String maxCol;

    /**
     * Name of the labelling column which the caller is interested in. The
     * caller perhaps likes to identify the record based on this value;
     * uniqueness over all records is not guaranteed though.
     */
    private String labelCol;

    /**
     * Whether the input has been fully processed.
     */
    private boolean consumed;

    /**
     * Cached value for minimum spread (while reading: minimum spread so far).
     */
    private int minSpread = -1;

    /**
     * Cached value from the label column for the record with the minimum spread
     * (while reading: with the minimum spread so far).
     */
    private String minSpreadLabel;

    /**
     * Constructs a new CSV data analyzer. Assumes that the data is encoded in
     * UTF-8.
     *
     * @param in  stream from which the input data can be read.
     * @param minColName  name of the column which holds minimum values.
     * @param maxColName  name of the column which holds maximum values.
     * @param labelColName  name of the column which shall be used as
     *         identifying label for records.
     */
    public CSVDataAnalyzer(final InputStream in,
            final String minColName, final String maxColName,
            final String labelColName) {
        this.in = in;

        minCol = minColName;
        maxCol = maxColName;
        labelCol = labelColName;
    }

    /**
     * Parses all input and sets the appropriate internal fields. Closes the
     * input when done. Does nothing if the input has already been read.
     * <P>
     * This method is automatically invoked when analyzed data is queried from
     * this class and the input has not yet been processed.
     *
     * @throws IOException if there is an I/O error (including malformed input).
     */
    private void readInput() throws IOException {
        if (consumed) return;

        final CSVParser parser =
                CSVParser.parse(in, StandardCharsets.UTF_8, CSVFormat.DEFAULT);

        for (CSVRecord rec : parser) {
            int min, max;
            try {
                min = Integer.parseInt(rec.get(minCol));
                max = Integer.parseInt(rec.get(maxCol));
            } catch (NumberFormatException e) {
                throw new IOException("malformed number in input", e);
            } catch (IllegalArgumentException e) {
                throw new IOException("wrong header or malformed input", e);
            }

            int spread = max - min;
            // XXX: the challenge does not define which record to use in
            // case of several minimum spread records (same value).
            // Assume that the first record is wanted.
            if (minSpreadLabel == null || spread < minSpread) {
                minSpread = spread;
                minSpreadLabel = rec.get(labelCol);
            }
        }

        parser.close();
        consumed = true;
    }

    /**
     * Returns the label for the record with the minimum spread between the
     * values in the minimum and maximum value columns.
     *
     * @return the record label, taken from the previously set labelling column.
     *
     * @throws IOException if there is an I/O error on processing the input.
     */
    /*
     * Implementation note:
     * automatically reads the input data if this has not been done yet. This is
     * the only reason to have IOException in the prototype at all. If calling
     * code frequently makes use of this method, may wrap it in a runtime
     * exception, and make readInput() publicly available. It can then be called
     * explicitly by external code.
     */
    public String getMinSpreadLabel() throws IOException {
        if (!consumed) {
            readInput();
        }

        return minSpreadLabel;
    }

}
