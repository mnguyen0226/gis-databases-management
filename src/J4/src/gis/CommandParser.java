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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.RandomAccessFile;
import java.util.Arrays;
import gis.objectmodel.Command;

/**
 * Parses a Command Script File
 */
public final class CommandParser {
    // PUBLIC METHODS

    /**
     * Parses a command script and outputs an ArrayList filled with commands
     *
     * @return An ArrayList of mypackages.Models.Command objects
     */
    public static ArrayList<Command> getCommands(String scriptFileName) {
        try {
            RandomAccessFile script_file = new RandomAccessFile(scriptFileName, "r");
            ArrayList<Command> commandList = new ArrayList<>();
            String line = script_file.readLine(); // first line of the file

            while (line != null) {
                while (line.trim().isEmpty()) { // if the line is empty then read the next line
                    line = script_file.readLine();
                }
                if (line.charAt(0) != ';') {
                    String[] sections = line.split("\t"); // split line thru tag
                    Command command = new Command(sections[0]); // command is task in command
                    Arrays.stream(sections).skip(1).forEach(command::addArg);
                    commandList.add(command);
                } else {
                    Command command = new Command("comment"); // mark lines with ; to be commenting lines
                    command.addArg(line);
                    commandList.add(command);
                }
                // System.out.println("TESTING CommandParser::getCommands: " + line + ".");
                line = script_file.readLine(); // go to next line
            }
            script_file.close(); // close access file manually
            return commandList;
        } catch (FileNotFoundException e) {
            System.err.println("Error: File " + scriptFileName + " can't be found.");
            return null;
        } catch (IOException e) {
            System.err.println("Error: Can't open and read script file.");
            return null;
        }
    }
}
