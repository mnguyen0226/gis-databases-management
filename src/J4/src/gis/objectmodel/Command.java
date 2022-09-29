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

package gis.objectmodel;

import java.util.ArrayList;

/**
 * Models a command created via the mypackages.Utility.CommandParser
 */
public class Command {
    // FIELDS
    private String task; // task that command use
    private ArrayList<String> args = new ArrayList<>();

    // CONSTRUCTORS

    /**
     * Create a new mypackages.Models.Command object
     *
     * @param task The task to execute
     */
    public Command(String task) {
        this.task = task;
    }

    /**
     * Get the task that this command uses
     * 
     * @return The task this command uses
     */
    public String getTask() {
        return task;
    }

    /**
     * Get the arguments that this command uses
     * 
     * @return A copied ArrayList of arguments that this command uses
     */
    public ArrayList<String> getArgs() {
        return new ArrayList<String>(args);
    }

    /**
     * Add an argument for this command
     *
     * @param arg An argument to use
     */
    public void addArg(String arg) {
        args.add(arg);
    }
}
