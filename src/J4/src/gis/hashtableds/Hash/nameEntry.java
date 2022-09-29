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
// Minh Nguyen
// mnguyen0226

package gis.hashtableds.Hash;

import java.util.ArrayList;

/**
 * Associates file offsets based on a GIS record's feature name
 */
public class nameEntry implements Hashable<nameEntry> {
    // FIELDS
    private String key; // GIS feature name
    private ArrayList<Long> locations; // file offsets of matching records - THIS IS THE OFFSET

    /**
     * Initialize a new nameEntry object with the given feature name and a single
     * file offset.
     * 
     * @param name   - the name of the state + state abbreviation
     * @param offset - the offset of the state stored in the the hashtable
     */
    public nameEntry(String name, Long offset) {
        this.key = name;
        locations = new ArrayList<Long>();
        locations.add(offset);
    }

    /**
     * Get the key for this nameEntry object
     * 
     * @return the key for this nameEntry object
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the locations for this nameEntry object
     * 
     * @return A reference to an ArrayList of locations
     */
    public ArrayList<Long> getOffsets() {
        return locations;
    }

    /**
     * Append a file offset to the existing list
     *
     * @param offset the file offset to add
     * @return true iff adding the offset was successful. False if not
     */
    public boolean addLocation(Long offset) {
        locations.add(offset);
        return true;
    }

    /**
     * Donald Knuth hash function for strings. You MUST use this.
     * 
     * @return hashValue & 0x0FFFFFFF - hashing value for each added record
     */
    public int Hash() { // Given // DEK hash fn => Called in Hasable.java
        int hashValue = key.length();
        for (int i = 0; i < key.length(); i++) {
            hashValue = ((hashValue << 5) ^ (hashValue >> 27)) ^ key.charAt(i);
        }
        return (hashValue & 0x0FFFFFFF);
    }

    /**
     * Compare two name entry objects.
     *
     * @param other The other nameEntry object
     * @return True iff they hold the same feature name. False if not
     */
    @Override
    public boolean equals(Object other) { // from J1
        if (other != null && other.getClass() == nameEntry.class) {
            return key.equals(((nameEntry) other).getKey());
        }
        return false;
    }

    /**
     * Get a string representation of the nameEntry object
     *
     * @return A string representation of the nameEntry object in the format needed
     *         for this assignment
     */
    @Override
    public String toString() {
        return ("[" + this.key + ", " + this.locations.toString() + "]");
    }
}