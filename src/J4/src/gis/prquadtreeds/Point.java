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

import java.util.ArrayList;

import gis.objectmodel.Area;

/**
 * Associates file offsets based on a GIS record's geographical coordinate For
 * documentation purposes, the term P refers to this object as a point
 */
public class Point implements Compare2D<Point> {
    // FIELDS
    private long xcoord;
    private long ycoord;
    private ArrayList<Long> offsetList = new ArrayList<Long>();

    // CONSTRUCTORS

    /**
     * Create a new CoordinateEntry object
     *
     * @param x The x coordinate of the entry, i.e. longitude
     * @param y The y coordinate of the entry, i.e. latitude
     */
    public Point(long x, long y) {
        this.xcoord = x;
        this.ycoord = y;
    }

    /**
     * Create a new CoordinateEntry object with a known offset
     * 
     * @param x      The x coordinate of the entry, i.e. longitude
     * @param y      The y coordinate of the entry, i.e. latitude
     * @param offset The offset to insert
     */
    public Point(long x, long y, long offset) {
        this(x, y);
        offsetList.add(offset);
    }

    /**
     * The x coordinate of the entry
     *
     * @return The x coordinate
     */
    public long getX() {
        return xcoord;
    }

    /**
     * The y coordinate of the entry
     *
     * @return The y coordinate
     */
    public long getY() {
        return ycoord;
    }

    /**
     * Get the offsets for this coordinate
     *
     * @return An array list of offsets
     */
    public ArrayList<Long> getOffsetList() {
        return offsetList;
    }

    /**
     * Add an offset for this coordinate
     *
     * @param offset The offset value to store
     */
    public void addOffset(long offset) {
        offsetList.add(offset);
    }

    /**
     * Determines which quadrant of the specified region P lies in.
     *
     * @param region The region of interest
     * @return NOQUADRANT if P does not lie in this region. Otherwise, returns the
     *         quadrant where P lies in
     */
    public Direction inQuadrant(Area a) {
        // exit if the point not in the area
        if (!this.inBox(a)) {
            return Direction.NOQUADRANT;
        }

        double midX = (a.getXLow() + a.getXHigh()) / 2.0;
        double midY = (a.getYHigh() + a.getYLow()) / 2.0;

        if ((Double.compare(this.xcoord, midX) == 0) && (Double.compare(this.ycoord, midY) == 0)) {
            return Direction.NE; // Point P at center => NE
        } else if ((Double.compare(this.xcoord, midX) > 0) && (Double.compare(this.ycoord, midY) >= 0)) {
            return Direction.NE;
        } else if ((Double.compare(this.xcoord, midX) <= 0) && (Double.compare(this.ycoord, midY) > 0)) {
            return Direction.NW;
        } else if ((Double.compare(this.xcoord, midX) < 0) && (Double.compare(this.ycoord, midY) <= 0)) {
            return Direction.SW;
        } else if ((Double.compare(this.xcoord, midX) >= 0) && (Double.compare(this.ycoord, midY) < 0)) {
            return Direction.SE;
        }
        return null;
    }

    /**
     * Checks if P lies within a region
     *
     * @param region The region of interest
     * @return True iff P lies in the specified region. False if not.
     */
    public boolean inBox(Area a) {
        if (this.xcoord >= a.getXLow() && this.xcoord <= a.getXHigh() && this.ycoord >= a.getYLow()
                && this.ycoord <= a.getYHigh()) {
            return true;
        }
        return false;
    }

    /**
     * Compares P with another CoordinateEntry
     *
     * @param o The other CoordinateEntry
     * @return True iff both P and other other CoordinateEntry have the same (x, y).
     *         False if not.
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null) {
            return false;
        } else if (!this.getClass().equals(o.getClass())) {
            return false;
        } // else if (getX() == ((Point) o).getX() && getY() == ((Point) o).getY()) {
          // return true;
        else {
            Point other = (Point) o;
            return (Double.compare(other.getX(), this.xcoord) == 0) && (Double.compare(other.getY(), this.ycoord) == 0);
        }
    }

    /**
     * Generates a string representation of P
     *
     * @return A string representing P
     */
    public String toString() {

        StringBuilder bd = new StringBuilder();

        bd.append(String.format("[(%d, %d), ", (long) xcoord, (long) ycoord));
        for (int i = 0; i < offsetList.size(); i++) {
            bd.append(offsetList.get(i));
            if (i != offsetList.size() - 1) {
                bd.append(", ");
            }
        }
        bd.append("]");
        return bd.toString();
    }
}
