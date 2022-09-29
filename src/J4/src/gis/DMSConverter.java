// On my honor:

//
// - I have not discussed the Java language code in my program with
// anyone other than my instructor or the teaching assistants
// assigned to this course.
//
// - I have not used Java language code obtained from another student,
// or any other unauthorized source, including the Internet, either
// modified or unmodified.
//
// - If any Java language code or documentation used in my program
// was obtained from another source, such as a text book or course
// notes, that has been clearly noted with a proper citation in
// the comments of my program.
//
// - I have not designed this program in such a way as to defeat or
// interfere with the normal operation of the supplied grading code.
//
// Minh T. Nguyen
// mnguyen0226

package gis;

/**
 * Converts a DMS string into different representations
 */
public class DMSConverter {

    /**
     * Convert a DMS string into a decimal value of total seconds
     *
     * <pre>
     *     The DMS string is in the proper form as XXXXXXN or XXXXXXXW where X is a number
     * </pre>
     *
     *
     * @param dms The DMS string
     * @return The total number of seconds
     */
    public static long toSecond(String dms) {
        if (dms.contains("W")) { // Longitude
            int degree = Integer.parseInt(dms.substring(0, 3));
            int minute = Integer.parseInt(dms.substring(3, 5));
            int seconds = Integer.parseInt(dms.substring(5, 7));
            return -((3600L * degree) + (60L * minute) + seconds);
        } else if (dms.contains("N")) { // Latitude
            int degree = Integer.parseInt(dms.substring(0, 2));
            int minute = Integer.parseInt(dms.substring(2, 4));
            int seconds = Integer.parseInt(dms.substring(4, 6));
            return ((3600L * degree) + (60L * minute) + seconds);
        } else {
            throw new NumberFormatException("Unable to process input DMS");
        }
    }

    /**
     * Convert a DMS string into a neat format.
     *
     * <pre>
     *     The DMS string is in the proper form as XXXXXXN or XXXXXXXW where X is a number
     * </pre>
     *
     * <post> The return result will be the DMS string in a proper format e.g.
     * 136810N --> 13d 68m 10s North </post>
     *
     *
     * @param dms The DMS string
     * @return A string representation of the DMS in a neater format
     */
    public static String toProper(String dms) {
        if (dms.contains("W")) { // longitude
            return String.format("%dd %dm %ds West", Integer.parseInt(dms.substring(0, 3)),
                    Integer.parseInt(dms.substring(3, 5)), Integer.parseInt(dms.substring(5, 7)));
        } else if (dms.contains("N")) { // Latitude
            return String.format("%dd %dm %ds North", Integer.parseInt(dms.substring(0, 2)),
                    Integer.parseInt(dms.substring(2, 4)), Integer.parseInt(dms.substring(4, 6)));
        } else {
            throw new NumberFormatException("Unable to process input DMS");
        }
    }
}