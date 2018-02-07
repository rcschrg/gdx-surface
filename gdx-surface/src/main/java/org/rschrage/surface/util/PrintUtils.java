package org.rschrage.surface.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Contains methods related to printing to standard output.
 *
 * @author Rico Schrage
 */
public class PrintUtils {

    /** Reference to the standard output stream */
    private static final PrintStream ORIGINAL = System.out;

    /** Dummy print-stream to prevent any output */
    private static final PrintStream DUMMY = new PrintStream(new OutputStream() {
        @Override
        public void write(int b) throws IOException {
            // do nothing
        }
    });

    private PrintUtils() {
        //utility class
    }

    /**
     * Enables writing to standard output stream.
     */
    public static void enableSystemOut() {
        System.setOut(ORIGINAL);
    }

    /**
     * Disables writing to standard output stream.
     */
    public static void disableSystemOut() {
        System.setOut(DUMMY);
    }
}
