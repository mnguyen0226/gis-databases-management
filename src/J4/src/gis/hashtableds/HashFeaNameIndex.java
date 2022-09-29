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

package gis.hashtableds;

import java.io.FileWriter;
import java.io.IOException;

import gis.hashtableds.Hash.hashTable;
import gis.hashtableds.Hash.nameEntry;

/**
 * Wrapper class for the HashTable as a name index
 */
public class HashFeaNameIndex {
    // FIELDS
    private hashTable<nameEntry> ht;

    // CONSTRUCTORS

    /**
     * Create a new FeatureNameIndex object
     * 
     * @param size   The size of the index
     * @param dLimit The load limit of the index
     */
    public HashFeaNameIndex(Integer size, Double loadLimit) {
        ht = new hashTable<>(size, loadLimit);
    }

    /**
     * Add to the feature name index
     * 
     * @param featureName       The feature name to add
     * @param stateAbbreviation The state abbreviation of the record
     * @param offset            The offset in which the record can be found
     * @return True if adding was successful. False if not
     */
    public boolean insert(String featureName, String stateAbb, Long offset) {
        // System.out.println("TESING: HASH");
        return ht.insert(new nameEntry(String.format("%s:%s", featureName, stateAbb), offset));
    }

    /**
     * Find a particular entry in the Feature Name Index
     * 
     * @param featureName       The feature name
     * @param stateAbbreviation The state abbreviation
     * @return A NameEntry object. NULL if no entry could be found
     */
    public nameEntry find(String featureName, String stateAbb) {
        String key = String.format("%s:%s", featureName, stateAbb);
        return ht.find(new nameEntry(key, -1L));
    }

    /**
     * Write the contents of this Feature Name Index to a file
     * 
     * @param fw The file writer to use
     */
    public void display(FileWriter raf) throws IOException {
        ht.display(raf);
    }
}
