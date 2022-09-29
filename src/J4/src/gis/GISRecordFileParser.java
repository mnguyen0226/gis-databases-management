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

import java.io.RandomAccessFile;

import gis.bufferpoolds.*;
import gis.hashtableds.HashFeaNameIndex;
import gis.objectmodel.GISRecord;
import gis.prquadtreeds.PRQuadCoorIndex;

import java.io.IOException;

/**
 * Utility class for parsing GIS records. Ideally, a new GISRecordFileParser is
 * created for every command
 */
public class GISRecordFileParser {
    // FIELDS
    private int trackedNumFeatures = 0;
    private int trackedNumLocations = 0;
    private int nameLength = 0;

    // CONSTRUCTORS
    /**
     * Initialize a GISRecordFileParser object
     */
    public GISRecordFileParser() {

    }

    /**
     * Get the average feature name length
     *
     * @return The average feature name length
     */
    public long getAverageNameLength() {
        return nameLength / trackedNumFeatures;
    }

    /**
     * Get the number of imported features
     *
     * @return The number of imported features
     */
    public long gettrackedNumFeatures() {
        return trackedNumFeatures;
    }

    /**
     * Get the number of imported locations
     *
     * @return The number of imported locations
     */
    public long gettrackedNumLocations() {
        return trackedNumLocations;
    }

    // PUBLIC METHODS

    /**
     * Generates a full GISRecord object from a specified offset
     *
     * <pre>
     *     databaseFileName is a valid file, offset is with respect to the created database, bufferPool is not null
     * </pre>
     *
     * @param databaseFileName The name of the truncated database file
     * @param offset           The file offset in which the record is located
     * @return A DS.BufferPoolPackage.GISRecord object; Null if an error occurs.
     */
    public GISRecord retrieveRecord(String dbFileName, long offset, BufferPool bp) {
        String rawRecord = bp.findBuffer(offset);

        // If able to find a record then get the record from the buffer pool
        if (rawRecord != null) {
            return new GISRecord(rawRecord);
        } else { // If can't find anything in the bufferpool then get the record from RAM
            try {
                RandomAccessFile gisdbRAF = new RandomAccessFile(dbFileName, "r");
                gisdbRAF.seek(offset);
                rawRecord = gisdbRAF.readLine(); // Skip the first line
                bp.insertBuffer(offset, rawRecord);

                gisdbRAF.close();
                return new GISRecord(rawRecord);
            } catch (IOException e) {
                System.err.println("Error: Can't open and read customized database");
            }
        }
        return null;
    }

    /**
     * Copies the contents from a larger database into a truncated database. Also
     * appends contents from the larger database into the coordinate and name index
     *
     * <pre>
     *     gisFileName, databaseFileName are valid files. coordinateIndex and nameIndex are not null
     * </pre>
     *
     * @param gisFileName      The name of the larger database text file
     * @param databaseFileName The name of the truncated database file
     * @param coordinateIndex  The coordinate index to append
     * @param nameIndex        The name index to append
     */
    public void customizeDB(String gisFileName, String dbFileName, PRQuadCoorIndex coorIndex,
            HashFeaNameIndex nameIndex) {
        try {
            RandomAccessFile gisRecordRAF = new RandomAccessFile(gisFileName, "r");
            RandomAccessFile customDBRAF = new RandomAccessFile(dbFileName, "rw");

            String record = gisRecordRAF.readLine(); // first line of the customed dataset
            if (customDBRAF.length() == 0) { // when therre is nothing then write the first non-record line
                customDBRAF.write((record + "\n").getBytes()); // Write header
            }

            customDBRAF.seek(customDBRAF.length());
            long offset = customDBRAF.getFilePointer();
            record = gisRecordRAF.readLine(); // get the first record of the custom dataset

            while (record != null && record.length() > 0) {
                String[] sections = record.split("\\|"); // split the field in the record by |
                if (nameIndex.insert(sections[1], sections[3], offset)) { // HASH TABLE
                    trackedNumFeatures = trackedNumFeatures + 1;
                    nameLength = nameLength + sections[1].length();

                    customDBRAF.write((record + "\n").getBytes());

                    if (!sections[7].contains("Unknown") || !sections[8].contains("Unknown")) {
                        if (coorIndex.insertPR(sections[8], sections[7], offset)) { // PRQUADTREE
                            trackedNumLocations = trackedNumLocations + 1;
                        }
                    }
                    customDBRAF.seek(customDBRAF.length()); // got to the next line for reading
                    offset = customDBRAF.getFilePointer();
                }
                record = gisRecordRAF.readLine(); // read next line
            }
            customDBRAF.close();
            gisRecordRAF.close();
        } catch (IOException e) {
            System.err.println("Error: Can't open and read the databased for import");
        }
    }
}