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

import gis.DMSConverter;

/**
 * GIS Record model, which only contains the relevant fields This will primarily
 * be used in conjunction with the DS.BufferPoolPackage.BufferPool
 */
public class GISRecord {
    // FIELDS
    private final long featureID;
    private final String featureName;
    private final String featureClass;
    private final String stateAlpha;
    private final String countyName;
    private final String latDMS;
    private final String longDMS;
    private final double latDec;
    private final double longDec;

    // CONSTRUCTORS

    /**
     * Create a GIS Record object out of a GIS record string
     *
     * <pre>
     *      The gisRecordString is not null and represents a raw record from a GIS.txt file
     * </pre>
     *
     * <post> A GISRecord object will be created where the relevant fields from the
     * raw record can be accessed as properties </post>
     *
     * @param gisRecordString The GIS record string to parse
     */
    public GISRecord(String gisString) {
        String[] fields = gisString.split("\\|");
        this.featureID = Long.parseLong(fields[0]);
        this.featureName = fields[1];
        this.featureClass = fields[2];
        this.stateAlpha = fields[3];
        this.countyName = fields[5];
        this.latDMS = DMSConverter.toProper(fields[7]);
        this.longDMS = DMSConverter.toProper(fields[8]);
        this.latDec = Double.parseDouble(fields[9]);
        this.longDec = Double.parseDouble(fields[10]);
    }

    /**
     * Get the feature ID
     *
     * @return the feature ID
     */
    public long getFeatureID() {
        return featureID;
    }

    /**
     * Getter feature name
     * 
     * @return feature name - the record's feature name
     */
    public String getFeatureName() {
        return featureName;
    }

    /**
     * Get the feature class
     *
     * @return the feature class
     */
    public String getFeatureClass() {
        return featureClass;
    }

    /**
     * Get the state alpha
     *
     * @return the state alpha
     */
    public String getStateAlpha() {
        return stateAlpha;
    }

    /**
     * Get the county name
     *
     * @return the county name
     */
    public String getCountyName() {
        return countyName;
    }

    /**
     * Get the latitude in DMS
     *
     * @return the latitude in DMS
     */
    public String getLatDMS() {
        return latDMS;
    }

    /**
     * Get the longitude in DMS
     *
     * @return he longitude in DMS
     */
    public String getLongDMS() {
        return longDMS;
    }

    /**
     * Get the latitude in decimal representation
     *
     * @return the latitude in decimal representation
     */
    public double getLatDec() {
        return latDec;
    }

    /**
     * Get the longitude in decimal representation
     *
     * @return the longitude in decimal representation
     */
    public double getLongDec() {
        return longDec;
    }

    /**
     * Get the string representation of a GIS record
     *
     * @return The string representation of a GIS record
     */
    @Override
    public String toString() {
        return String.format(
                "Feature ID: %d \n" + "Feature Class: %s \n" + "State Alpha: %s \n" + "Primary Latitude DMS: %s \n"
                        + "Primary Longitude DMS: %s \n" + "Primary Latitude Decimal: %s \n"
                        + "Primary Longitude Decimal: %s \n",
                featureID, featureClass, stateAlpha, latDMS, longDMS, latDec, longDec);
    }

    /**
     * Compare two DS.BufferPoolPackage.GISRecord objects
     * 
     * @param other The other DS.BufferPoolPackage.GISRecord object to compare with
     * @return True if all fields are equal. False if not.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (!o.getClass().equals(GISRecord.class)) {
            return false;
        } else {
            GISRecord oRecord = (GISRecord) o;

            if (this.featureID == oRecord.getFeatureID() && this.featureName.equals(oRecord.getFeatureName())
                    && this.featureClass.equals(oRecord.getFeatureClass())
                    && this.stateAlpha.equals(oRecord.getStateAlpha()) && this.latDMS.equals(oRecord.getLatDMS())
                    && this.longDMS.equals(oRecord.getLongDMS()) && this.latDec == oRecord.getLatDec()
                    && this.longDec == oRecord.getLongDec()) {
                return true;
            }
            return false;
        }
    }
}
