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

/**
 * Defines a rectangular region by bottom left corner and top right corner
 */
public class Area {
    // Fields: Variables that are necessary to create an area
    private final long xLow;
    private final long xHigh;
    private final long yLow;
    private final long yHigh;

    /**
     * Constructor: create the area class
     * 
     * @param xLow  - bottom left x
     * @param xHigh - top right x
     * @param yLow  - bottom left y
     * @param yHigh - top right y
     */
    public Area(long xLow, long xHigh, long yLow, long yHigh) {
        this.xLow = xLow;
        this.xHigh = xHigh;
        this.yLow = yLow;
        this.yHigh = yHigh;
    }

    /**
     * Getter x low
     * 
     * @return xLow - bottom left x
     */
    public long getXLow() {
        return xLow;
    }

    /**
     * Getter x high
     * 
     * @return xHigh - top right x
     */
    public long getXHigh() {
        return xHigh;
    }

    /**
     * Getter y low
     * 
     * @return yLow - bottom left y
     */
    public long getYLow() {
        return yLow;
    }

    /**
     * Getter y high
     * 
     * @return yHigh - top right y
     */
    public long getYHigh() {
        return yHigh;
    }

    /**
     * Function calculate the middle point of the X coordinate for splitting
     * 
     * @return midX - middle point of X coordinate of the area
     */
    public long getMidX() {
        long midX = (long) ((xLow + xHigh) / 2.0);
        return midX;
    }

    /**
     * Function calculate the middle point of the Y coordinate for splitting
     * 
     * @return midY - middle point of Y coordinate of the area
     */
    public long getMidY() {
        long midY = (long) ((yLow + yHigh) / 2.0);
        return midY;
    }

    /**
     * Function check whether the two areas accidentally intersect or not
     * 
     * @param a - other considering area
     * @return true - if they intersect
     * @return false -if they do not intersect
     */
    public boolean checkIntersection(Area a) {
        if ((xLow <= a.getXHigh()) && (xHigh >= a.getXLow()) && (yLow <= a.getYHigh()) && (yHigh >= a.getYLow())) {
            return true;
        }
        return false;
    }
}
