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

import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import gis.bufferpoolds.*;
import gis.hashtableds.*;

import gis.hashtableds.Hash.nameEntry;
import gis.objectmodel.Command;
import gis.objectmodel.GISRecord;
import gis.prquadtreeds.*;
import gis.objectmodel.Area;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Processes commands given a command script
 */
public class CommandProcessor {
    // FIELDS
    private final String dbFileName; // name of the customed database
    private final String scriptFileName; // name of the script command
    private final String logFileName; // name of the output log file

    private PRQuadCoorIndex coorIndexer; // coordinate index of the GIS record returned from prQuadTree
    private HashFeaNameIndex nameIndexer; // feature name index of the GIS record return from HashTable
    private BufferPool bufpool; // bufferpool object get the GIS records based on the offset

    private int commandCount = 0; // Keep track of the number of command to print out to the log

    // Fields that keep times:
    private SimpleDateFormat sdf = new SimpleDateFormat("EEE");
    private String string_date = sdf.format(new Date());
    private SimpleDateFormat sdf_month = new SimpleDateFormat("MMM");
    private String string_month = sdf_month.format(new Date());
    private SimpleDateFormat sdf_day_time = new SimpleDateFormat("dd HH:mm:ss");
    private String string_day_time = sdf_day_time.format(new Date());

    // CONSTRUCTORS

    /**
     * Create a new mypackages.Utility.CommandProcessor object
     *
     * @param commands         The list of commands to process
     * @param databaseFileName The name of the our truncated database file
     * @param logFileName      The name of the log file to write output to
     */
    public CommandProcessor(ArrayList<Command> commandList, String dbFileName, String scriptFileName,
            String logFileName) {
        this.dbFileName = dbFileName;
        this.scriptFileName = scriptFileName;
        this.logFileName = logFileName;

        // Create a new log file
        try {
            FileWriter fw = new FileWriter(logFileName);
            for (Command c : commandList) {
                processCommandList(c);
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.err.println("Error: Can't create a new output log file");
        }
    }

    /**
     * Process an individual command
     *
     * <pre>
     *     command contains a valid task and at least 1 argument (unless comment or quit)
     * </pre>
     *
     * @param command The command to process
     */
    private void processCommandList(Command command) {
        if (command.getTask().equals("world")) {
            executeWorld(command.getArgs().get(0), command.getArgs().get(1), command.getArgs().get(2),
                    command.getArgs().get(3));
        } else if (command.getTask().equals("import")) {
            commandCount = commandCount + 1;
            executeImport(command.getArgs().get(0));

        } else if (command.getTask().equals("debug")) {
            commandCount = commandCount + 1;
            executeDebug(command.getArgs().get(0));
            // System.out.println("TESTING: ComPro::processCommand " +
            // command.getArgs().get(0));

        } else if (command.getTask().equals("what_is")) {
            commandCount = commandCount + 1;
            executeWhatIs(command.getArgs().get(0), command.getArgs().get(1));

        } else if (command.getTask().equals("what_is_at")) {
            commandCount = commandCount + 1;
            executeWhatIsAt(command.getArgs().get(0), command.getArgs().get(1));

        } else if (command.getTask().equals("what_is_in")) {
            commandCount = commandCount + 1;
            executeWhatIsIn(command.getArgs().get(0), command.getArgs().get(1), command.getArgs().get(2),
                    command.getArgs().get(3));

        } else if (command.getTask().equals("quit")) {
            commandCount = commandCount + 1;
            executeQuit();
        } else if (command.getTask().equals("comment")) {
            executeComment(command.getArgs().get(0));

        } else {
            commandCount = commandCount + 1;
            executeElse(command.getTask(), command.getArgs());
        }
    }

    /**
     * Execute the comment command
     *
     * @param comment The comment to write
     */
    private void executeComment(String comment) {
        try {
            FileWriter fw = new FileWriter(logFileName, true);
            fw.write(comment + "\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.err.println("Error: Can't open and write in the output log file");
        }
    }

    /**
     * Execute whenever an unsupported command is read
     *
     * @param task The task of the command
     * @param args The arguments of the command
     */
    private void executeElse(String task, ArrayList<String> args) {
        try {
            FileWriter fw = new FileWriter(logFileName, true);
            fw.write(String.format("Command %d : %s %s \n\n", commandCount, task,
                    Arrays.toString(args.toArray()).replace("[", "").replace("]", "").replace(",", "\t")));
            fw.write(String.format("Unrecognized // Unsupported command! \n"));
            fw.write("--------------------------------------------------------------------------------\n\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.err.println("Error: Can't open and write in the output log file");
        }
    }

    /**
     * Function execute the "world" command
     * 
     * Pre: the object must be String types
     * 
     * Post: Function successfully process "world" in the script file
     * 
     * @param westLongitude - String west longitude
     * @param eastLongitude - String east longitude
     * @param southLatitude - String south latitude
     * @param northLatitude - String north latitude
     */
    private void executeWorld(String westLongitude, String eastLongitude, String southLatitude, String northLatitude) {
        // Convert long type to seconds
        long westSec = DMSConverter.toSecond(westLongitude);
        long eastSec = DMSConverter.toSecond(eastLongitude);
        long southSec = DMSConverter.toSecond(southLatitude);
        long northSec = DMSConverter.toSecond(northLatitude);

        // Initialize coordinate index, feature name index, and bufferpool
        coorIndexer = new PRQuadCoorIndex(new Area(westSec, eastSec, southSec, northSec));
        nameIndexer = new HashFeaNameIndex(256, 1.0);
        bufpool = new BufferPool();

        // Create a new database base on other db - mark
        try {
            FileWriter newFW = new FileWriter(dbFileName); // this is for the import, do nothing, just create a folder
            // newFW.write(
            // "FEATURE_ID|FEATURE_NAME|FEATURE_CLASS|STATE_ALPHA|STATE_NUMERIC|COUNTY_NAME|COUNTY_NUMERIC|PRIMARY_LAT_DMS|PRIM_LONG_DMS|PRIM_LAT_DEC|PRIM_LONG_DEC|SOURCE_LAT_DMS|SOURCE_LONG_DMS|SOURCE_LAT_DEC|SOURCE_LONG_DEC|ELEV_IN_M|ELEV_IN_FT|MAP_NAME|DATE_CREATED|DATE_EDITED\n");
            newFW.flush();
            newFW.close();
        } catch (IOException e) {
            System.err.println("Error: Can't create a new custom database");
        }

        try {
            // Create a file writer object
            FileWriter fw = new FileWriter(logFileName, true);
            fw.write(String.format("world\t%s\t%s\t%s\t%s\n\n", westLongitude, eastLongitude, southLatitude,
                    northLatitude));
            fw.write("GIS Program\n\n");
            fw.write("dbFile:     " + dbFileName + "\n");
            fw.write("script:     " + scriptFileName + "\n");
            fw.write("log:        " + logFileName + "\n");
            fw.write("Start time: " + string_date + " " + string_month + " " + string_day_time + " EDT 2021\n");

            fw.write("Quadtree children are printed in the order SW  SE  NE  NW\n");
            fw.write("--------------------------------------------------------------------------------\n\n");
            fw.write("Latitude/longitude values in index entries are shown as signed integers, in total seconds.\n\n");
            fw.write("World boundaries are set to:\n");
            fw.write("              " + northSec + "\n");
            fw.write("   " + westSec + "                " + eastSec + "\n");
            fw.write("              " + southSec + "\n");
            fw.write("--------------------------------------------------------------------------------\n");
            fw.flush();
            fw.close();

        } catch (IOException e) {
            System.err.println("Error: Can't open and write in the output log file");
        }
    }

    /**
     * Execute the import command
     *
     * @param gisFileName The GIS file name to use
     */
    private void executeImport(String gisFileName) {
        try (FileWriter fw = new FileWriter(logFileName, true)) {
            // Create a file writer object
            // FileWriter fw = new FileWriter(logFileName, true);
            fw.write(String.format("Command %d: import \t %s\n\n", commandCount, gisFileName));

            // Create a GISRecordParser Object
            GISRecordFileParser gisRFP = new GISRecordFileParser();
            // System.out.println("TESTING: ComPro::executeImport");
            gisRFP.customizeDB(gisFileName, dbFileName, coorIndexer, nameIndexer); // create a customize db, store
                                                                                   // record based on name (hashtable)
                                                                                   // and coordinate (prquad)

            fw.write(String.format("Imported Features by name: %d\n", gisRFP.gettrackedNumFeatures()));
            fw.write(String.format("Imported Locations::       %d\n", gisRFP.gettrackedNumLocations()));
            fw.write(String.format("Average name length:       %d\n", gisRFP.getAverageNameLength()));
            fw.write("--------------------------------------------------------------------------------\n");
            // fw.flush();
            // fw.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: Can't find the record files to put into the custom database");
        } catch (IOException e) {
            System.err.println("Error: Can't open and write in the output log file");
        }
    }

    /**
     * Execute the what_is command
     *
     * @param featureName       The record's feature name
     * @param stateAbbreviation The record's state abbreviation
     */
    private void executeWhatIs(String featureName, String stateAbb) {
        try {
            FileWriter fw = new FileWriter(logFileName, true);
            fw.write(String.format("Command %d: what_is\t%s\t%s\n\n", commandCount, featureName, stateAbb));

            // Find the GIS record based on the feature name
            nameEntry resultOffset = nameIndexer.find(featureName, stateAbb); // return the offset
            GISRecordFileParser gisRFP = new GISRecordFileParser(); // things has been store in import, nowthis just for
                                                                    // finding record
            // System.out.println("TESTING: ComPro::executeWhatIs");

            if (resultOffset != null) {
                for (long offset : resultOffset.getOffsets()) {
                    GISRecord record = gisRFP.retrieveRecord(dbFileName, offset, bufpool);
                    assert record != null; // check to see if the record null or not
                    fw.write(String.format("    %4d: %s (%s, %s)\n", offset, record.getCountyName(),
                            record.getLongDMS(), record.getLatDMS()));
                }
            } else {
                fw.write(String.format("No records match %s and %s\n", featureName, stateAbb));
            }
            fw.write("--------------------------------------------------------------------------------\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.err.println("Error: Can't open and write in the output log file");
        }
    }

    /**
     * Execute the what_is_at command
     *
     * <pre>
     *     Each latitude/longitude is in the form XXXXXXN or XXXXXXXW where X is a number
     * </pre>
     *
     * @param latitudeString  The latitude coordinate
     * @param longitudeString The longitude coordinate
     */
    private void executeWhatIsAt(String latitude, String longitude) {
        try {
            FileWriter fw = new FileWriter(logFileName, true);
            fw.write(String.format("Command %d:  what_is_at\t%s\t%s\n\n", commandCount, latitude, longitude));

            // Search the GIS Records
            Point result = coorIndexer.findPR(longitude, latitude);
            GISRecordFileParser gisRFP = new GISRecordFileParser();

            if (result != null) {
                fw.write(String.format("   The following features were found at (%s, %s):\n",
                        DMSConverter.toProper(longitude), DMSConverter.toProper(latitude)));
                for (int i = 0; i < result.getOffsetList().size(); i++) {
                    GISRecord record = gisRFP.retrieveRecord(dbFileName, result.getOffsetList().get(i), bufpool);
                    assert record != null;
                    fw.write(String.format("    %4d:  %s  %s  %s\n", result.getOffsetList().get(i),
                            record.getFeatureName(), record.getCountyName(), record.getStateAlpha()));
                }
            } else {
                fw.write(String.format("   Nothing was found at (%s, %s)\n", DMSConverter.toProper(longitude),
                        DMSConverter.toProper(latitude)));
            }

            fw.write("--------------------------------------------------------------------------------\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.err.println("Error: Can't open and write in the output log file");
        }
    }

    /**
     * Execute the what_is_in command
     *
     * <pre>
     *     Each latitude/longitude is in the form XXXXXXN or XXXXXXXW where X is a number
     * </pre>
     *
     * @param latitudeString   The latitude coordinate
     * @param longitudeString  The longitude coordinate
     * @param halfHeightString The search region's height / 2
     * @param halfWidthString  The search region's width / 2
     */
    private void executeWhatIsIn(String latitude, String longitude, String areaHalfHeight, String areaHalfWidth) { // Unknown
        try {
            FileWriter fw = new FileWriter(logFileName, true);
            fw.write(String.format("Command %d:  what_is_in\t%s\t%s %s\t%s\n\n", commandCount, latitude, longitude,
                    areaHalfHeight, areaHalfWidth));

            // Get the record
            ArrayList<Point> results = coorIndexer.areaSearchPR(longitude, latitude, areaHalfHeight, areaHalfWidth);

            // System.out.println("TESTING: ComPro::execWhatIsIn " + results.size());
            GISRecordFileParser gisRAF = new GISRecordFileParser();

            // Count number of features
            int featureCount = 0;
            for (int i = 0; i < results.size(); i++) {
                for (int j = 0; j < results.get(i).getOffsetList().size(); j++) {
                    featureCount++;
                }
            }

            if (results.size() > 0) {
                fw.write(String.format("   The following %d features were found in (%s +/- %d, %s +/- %d)\n",
                        featureCount, DMSConverter.toProper(longitude), Long.parseLong(areaHalfWidth),
                        DMSConverter.toProper(latitude), Long.parseLong(areaHalfHeight)));
                for (int i = 0; i < results.size(); i++) {
                    for (int j = 0; j < results.get(i).getOffsetList().size(); j++) {
                        // System.out.println("TESTING: ComPro::ExecWhatIsIn---");
                        GISRecord record = gisRAF.retrieveRecord(dbFileName, results.get(i).getOffsetList().get(j),
                                bufpool);
                        assert record != null;
                        fw.write(String.format("    %4d: %s %s (%s, %s)\n", results.get(i).getOffsetList().get(j),
                                record.getFeatureName(), record.getStateAlpha(), record.getLongDMS(),
                                record.getLatDMS()));
                    }
                }
            } else {
                fw.write(String.format("   Nothing was found at (%s +/- %d, %s +/- %d)\n",
                        DMSConverter.toProper(longitude), Long.parseLong(areaHalfWidth),
                        DMSConverter.toProper(latitude), Long.parseLong(areaHalfHeight)));
            }

            fw.write("--------------------------------------------------------------------------------\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.err.println("Error: Can't open and write in the output log file");
        }

    }

    /**
     * Execute the debug command
     *
     * @param mode The mode for debugging. Can be quad, hash or pool
     */
    private void executeDebug(String debugMode) {
        try {
            FileWriter fw = new FileWriter(logFileName, true);
            fw.write(String.format("Command %d: debug %s\n\n", commandCount, debugMode));
            // System.out.println("TESTING: Compro::Debug");
            if (debugMode.equals("quad")) {
                coorIndexer.display(fw);
            } else if (debugMode.equals("hash")) {
                nameIndexer.display(fw);
            } else if (debugMode.equals("pool")) {
                bufpool.display(fw);
            } else if (debugMode.equals("world")) {
                fw.write("Debug world is not supported since it is optional \n");
            }
            fw.write("--------------------------------------------------------------------------------\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.err.println("Error: Can't open and write in the output log file");
        }
    }

    /**
     * Execute the quit command
     */
    private void executeQuit() {
        try {
            FileWriter fw = new FileWriter(logFileName, true);
            fw.write(String.format("Command %d: quit \n\n", commandCount));
            fw.write("\nTerminating execution of commands.\n");
            fw.write("End time: " + string_date + " " + string_month + " " + string_day_time + " EDT 2021\n");
            fw.write("--------------------------------------------------------------------------------\n");
            fw.flush();
            fw.close();

        } catch (IOException e) {
            System.err.println("Error: Can't open and write in the output log file");
        }
    }
}
