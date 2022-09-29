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

package gis.prquadtreeds;

import gis.DMSConverter;
import gis.objectmodel.Area;

import java.io.FileWriter;
import java.util.ArrayList;
import gis.prquadtreeds.PRQuadTree;

/**
 * Wrapper class for the PRQuadTree as a coordinate index
 */
public class PRQuadCoorIndex {
    // FIELDS
    private PRQuadTree<Point> qt;

    // CONSTRUCTORS

    /**
     * Create a new CoordinateIndex object
     * 
     * @param bounds The world boundaries for this coordinate index
     */
    public PRQuadCoorIndex(Area area) {
        qt = new PRQuadTree<>(area);
    }

    // PUBLIC METHODS

    /**
     * Add to the coordinate index
     * 
     * @param longitude The longitude coordinate
     * @param latitude  The latitude coordinate
     * @param offset    The offset in which the record can be found
     * @return True if adding was successful. False if not
     */
    public boolean insertPR(String loString, String latString, Long offset) {
        long longSec = DMSConverter.toSecond(loString);
        long latiSec = DMSConverter.toSecond(latString);
        if (qt.inBounds(new Point(longSec, latiSec))) {
            return qt.insert(new Point(longSec, latiSec, offset));
        }
        return false;
    }

    /**
     * Find a particular entry in the Coordinate Index
     * 
     * @param longitudeString The longitude as a string
     * @param latitudeString  The latitude as a string
     * @return A CoordinateEntry object. NULL if no entry could be found
     */
    public Point findPR(String loString, String latString) {
        return qt.find(new Point(DMSConverter.toSecond(loString), DMSConverter.toSecond(latString)));
    }

    /**
     * Perform a region search on the Coordinate Index
     * 
     * @param longitudeString  The longitude as a string
     * @param latitudeString   The latitude as a string
     * @param halfHeightString The half height as a string
     * @param halfWidthString  The half width as a string
     * @return An ArrayList of CoordinateEntry objects. Empty array list if no
     *         entries could be found.
     */
    public ArrayList<Point> areaSearchPR(String loString, String latString, String halfHiString, String halfWiString) {
        // Convert String to Long Type
        long longSec = DMSConverter.toSecond(loString);
        long latiSec = DMSConverter.toSecond(latString);
        long halfHi = Long.parseLong(halfHiString);
        long halfWi = Long.parseLong(halfWiString);
        // System.out.println("TESTING::AreaSearchPR ");

        // initialize search area
        Area searchArea = new Area(longSec - halfWi, longSec + halfWi, latiSec - halfHi, latiSec + halfHi);
        return qt.areaSearch(searchArea);
    }

    /**
     * Write the contents of this Coordinate Index to a file
     * 
     * @param fw The file writer to use
     */
    public void display(FileWriter fw) {
        qt.display(fw);
    }
}
