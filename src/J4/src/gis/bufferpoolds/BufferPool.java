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

package gis.bufferpoolds;

import java.io.IOException;
import java.util.LinkedList;
import java.io.FileWriter;

/**
 * Stores raw gis records paired with their file offset into a buffer for
 * dynamic access
 */
public class BufferPool {
    // FIELDS
    private int bufferMaxSize = 15;
    private LinkedList<Buffer> linkedBuffer;

    // CONSTRUCTOR
    public BufferPool() {
        linkedBuffer = new LinkedList<>();
    }

    /**
     * Defines a 2-way association between an offset and the corresponding raw GIS
     * record
     */
    private class Buffer {
        // Fields: Variables needed for the buffer
        private long offset;
        private String rawRecord;

        /**
         * Create a new buffer object
         *
         * @param offset the location of the record's string-entirety in the database
         * @param record the parsed GIS record object
         */
        public Buffer(long offset, String rawRecord) {
            this.offset = offset;
            this.rawRecord = rawRecord;
        }

        /**
         * Get the offset in the buffer
         *
         * @return The offset
         */
        public long getOffset() {
            return offset;
        }

        /**
         * Get the raw gis record string in the buffer
         *
         * @return The raw gis record string
         */
        public String getRawRecord() {
            return rawRecord;
        }

        /**
         * Compare two tuples
         *
         * @param other The other tuple
         * @return True if they have the same key. False if not
         */
        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            } else if (!o.getClass().equals(Buffer.class)) {
                return false;
            } else if (o == this) {
                return true;
            } else {
                Buffer otherBuffer = (Buffer) o;
                if (this.getOffset() == otherBuffer.getOffset()) {
                    return true;
                }
                return false;
            }
        }
    }

    /**
     * Get a raw GIS record string
     *
     * <pre>
     *     offset is with respect to the created database file.
     * </pre>
     *
     * @param offset the location of the GIS record's string-entirety in the
     *               database
     * @return A string representing a raw GIS record
     */
    public String findBuffer(long offset) {
        for (int i = 0; i < linkedBuffer.size(); i++) {
            if (offset == linkedBuffer.get(i).getOffset()) {
                Buffer gotIt = linkedBuffer.get(i);
                linkedBuffer.remove(i);
                linkedBuffer.add(0, gotIt);
                return gotIt.rawRecord;
            }
        }
        return null;
    }

    /**
     * Get a raw GIS record string
     *
     * <pre>
     *     offset is with respect to the created database file.
     * </pre>
     *
     * @param offset the location of the GIS record's string-entirety in the
     *               database
     * @return A string representing a raw GIS record
     */
    public boolean insertBuffer(long offset, String rawRecord) {
        if (rawRecord != null) {
            linkedBuffer.add(0, new Buffer(offset, rawRecord));
            if (linkedBuffer.size() > bufferMaxSize) {
                linkedBuffer.removeLast();
            }
            return true;
        }
        return false;
    }

    /**
     * Display the contents of the buffer pool to a file
     *
     * <pre>
     *     fw is open on an output file
     * </pre>
     *
     * @param fw The file writer to use
     */
    public void display(FileWriter fw) throws IOException {
        fw.write("MRU\n");
        for (int i = 0; i < linkedBuffer.size(); i++) {
            Buffer buffer = linkedBuffer.get(i);
            fw.write(String.format("    %4d:  %s\n", buffer.offset, buffer.rawRecord));
        }
        fw.write("LRU\n");
    }
}
