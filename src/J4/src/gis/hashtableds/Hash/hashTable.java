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
import java.util.LinkedList;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generic chained hash table
 *
 * @param <T> The type of objects this hash table will store
 */
public class hashTable<T extends Hashable<T>> {
    // FIELDS
    private ArrayList<LinkedList<T>> table; // physical basis for the hash table
    private Integer numElements = 0; // number of elements in all the chains
    private Double loadLimit = 0.7; // table resize trigger
    private final Integer defaultTableSize = 256; // default number of table slots
    private Integer maxLinkListSize = 0; // Default number of table slot

    // CONSTRUCTORS

    /**
     * Constructs an empty hash table
     *
     * <post> table is an ArrayList of size LinkedList objects; 256 slots if size ==
     * null loadLimit is set to default (0.7) if ldLimit == null </post>
     *
     * @param size    The user's desired number of lots; null for default
     * @param ldLimit The user's desired load factor limit for resizing the table;
     *                null for default
     */
    public hashTable(Integer size, Double ldLimit) {
        int table_add = 0;

        if (size != null) { // generate the hash table with size and Array List
            table = new ArrayList<LinkedList<T>>(size);
            table_add = size;
        } else { // if not specify the size, use the default size
            table = new ArrayList<LinkedList<T>>(defaultTableSize);
            table_add = defaultTableSize;
        }
        for (int i = 0; i < table_add; i++) {
            table.add(new LinkedList<T>());
        }

        if (ldLimit != null) { // set the limit of the hash table
            loadLimit = ldLimit;
        }
    }

    /**
     * Inserts elem at the front of the elem's home slot, unless that slot already
     * contains a matching element (according to the equals() method for the user's
     * data type.
     * 
     * <pre>
     *      elem is a valid user data object
     * </pre>
     * 
     * <post> elem is inserted unless it is a duplicate if the resulting load factor
     * exceeds the load limit, the table is rehashed with the size doubled </post>
     * 
     * @param T elem - generic object for insertion into the hash table
     * @return true - if able to insert
     * @return false - in unable to insert
     */
    public boolean insert(T elem) {
        //////////////// REHASHING - for collission
        if (((double) numElements / (double) table.size()) >= loadLimit) { // Calculate the load factor to insert in the
            ArrayList<LinkedList<T>> temp_table = table;// create a temp_table that has the same size as table
            int new_size = table.size() * 2; // double the size of the new table
            table = new ArrayList<LinkedList<T>>(new_size);

            numElements = 0; // for new table
            maxLinkListSize = 0; // for new table

            for (int m = 0; m < new_size; m++) {// create new slots
                table.add(new LinkedList<T>());
            }

            for (int n = 0; n < temp_table.size(); n++) {
                LinkedList<T> temp_list = temp_table.get(n);
                for (int t = 0; t < temp_list.size(); t++) {
                    insert(temp_list.get(t));
                }
            }
        }
        //////////////// END REHASHING

        int index = elem.Hash() % table.size(); // Get the index for the hash table
        int linked_list_index = table.get(index).indexOf(elem);

        if (linked_list_index == -1) { // if the linked list is empty
            table.get(index).add(elem);
            numElements++;

            if (table.get(index).size() > maxLinkListSize) {
                maxLinkListSize = table.get(index).size();
            }
            return true;
        } else if (linked_list_index >= 0) {// If there something already in the index

            ArrayList<Long> locs = ((nameEntry) elem).getOffsets();

            for (int i = 0; i < locs.size(); i++) {
                ((nameEntry) table.get(index).get(linked_list_index)).addLocation(locs.get(i));
            }

            return true;
        }
        return false;
    }

    /**
     * Searches the table for an element that matches elem (according to the
     * equals() method for the user's data type).
     * 
     * <pre>
     *    elem is a valid user data object
     * </pre>
     * 
     * @param T elem - generic object for insertion into the hash table
     * @return table.get(index).get(linked_list_index) - found object
     * @return null - not able to find the object
     */
    public T find(T elem) {
        int index = elem.Hash() % table.size();
        int linked_list_index = table.get(index).indexOf(elem);

        if (linked_list_index > -1) { // at the end of the linked list
            return table.get(index).get(linked_list_index);
        }
        return null;
    }

    /**
     * Writes a formatted display of the hash table contents
     *
     * <pre>
     *     fw is open on an output file
     * </pre>
     *
     * @param fw FileWriter object
     */
    public void display(FileWriter fw) throws IOException {
        fw.write(("Number of elements: " + numElements + "\n"));
        fw.write(("Number of slots: " + table.size() + "\n"));
        fw.write(("Maximum elements in a slot: " + maxLinkListSize + "\n"));
        fw.write(("Load limit: " + loadLimit + "\n"));
        fw.write(("\n"));

        fw.write(("Slot     Contents\n"));
        for (int idx = 0; idx < table.size(); idx++) {
            LinkedList<T> curr = table.get(idx);
            if (curr != null && !curr.isEmpty()) {
                fw.write((String.format("%5d: %s\n", idx, curr.toString())));
            }
        }
    }
}