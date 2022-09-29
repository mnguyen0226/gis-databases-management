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

/**
 * The design above is intended to take full advantage of the features in the
 * Java Library, in creating a user-defined alternative to the Library support
 * for hashing. You are not allowed to use the Library hash table, or the
 * hashCode() method.
 */
public interface Hashable<T> {
    public int Hash();
}