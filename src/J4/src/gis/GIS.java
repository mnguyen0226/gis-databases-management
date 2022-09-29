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

import gis.*;

/**
 * Main Driver class to execute the program
 */
public class GIS {
    /**
     * Main method
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println(
                    "Error: Please specify the database file name, command script file name, and log file name");
            return;
        }
        // Initialize the Controller class that take in the three arguments
        Controller controller = new Controller(args[0], args[1], args[2]);
    }
}
