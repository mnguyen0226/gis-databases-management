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
// interfere with the normal operation of the grading code.
//
// <Minh Nguyen>
// <mnguyen0226>

package gis.prquadtreeds;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import gis.objectmodel.Area;

/**
 * Generic PRQuadTree structure
 *
 * Quadrants are squares NW, NE, SE, SW such that NW: (xLo, centerY) to
 * (centerX, yHi) NE: (centerX, centerY) to (xHi, yHi) SE: (centerX, yLo) to
 * (xHi, centerY) SW: (xLo, yLo) to (centerX, centerY)
 *
 * @param <T> The type of objects that this PRQuadTree will store
 */
public class PRQuadTree<T extends Compare2D<? super T>> {
    // INNER CLASSES

    /**
     * Contructor PRQuadNode
     */
    private abstract class PRQuadNode {

    }

    /**
     * Constructor PRQuadLeaf
     */
    private class PRQuadLeaf extends PRQuadNode { // array list for leaves
        private ArrayList<T> Elements = new ArrayList<>();
    }

    /**
     * Constructor PRQuadInternal
     */
    private class PRQuadInternal extends PRQuadNode { // each internal node has 4 nodes
        private PRQuadNode NW, SW, SE, NE;
    }

    // FIELDS
    private PRQuadNode root;
    private Area area;
    private boolean operationOk; // McQuain style - flag for alternate on of recursion, aka insert ok?
    private final int BUCKET_CAPACITY = 4; // Change this to 4

    // CONSTRUCTOR(S)

    /**
     * Initialize quadtree to empty state. aka constructor
     * 
     * @param area - Considering area
     */
    public PRQuadTree(Area area) {
        this.area = area;
    }

    // PUBLIC METHODS

    /**
     * Insert an element into the PR quad tree
     *
     * @param elem The data to insert
     * @return True iff elem is inserted into the tree. False if not
     */
    public boolean insert(T elem) {
        if (elem == null) {
            return false; // Case 1: Item to be inserted does not exist
        } else if (!inBounds(elem)) {
            return false; // Case 2: Item to be inserted is not within world
        } else {
            root = insertHelper(root, elem, area); // Case 3..6
            return operationOk;
        }
    }

    /**
     * Helper function to insert data into a PR Quad Tree
     *
     * @param sRoot     The subroot for each recursive iteration
     * @param elem      The data to insert
     * @param subregion The subregion to narrow down for each recursive iteration
     * @return A pointer to the current node
     */
    private PRQuadNode insertHelper(PRQuadNode sRoot, T elem, Area subArea) {
        // Case 3 -- (relative) root is null // current node is null
        if (sRoot == null) {
            // Insert the new leaf node
            PRQuadLeaf newLeaf = new PRQuadLeaf();
            newLeaf.Elements.add(elem);
            operationOk = true;
            return newLeaf;
        }

        // Case 4, 5
        if (sRoot.getClass() == PRQuadLeaf.class) {
            PRQuadLeaf currLeaf = (PRQuadLeaf) sRoot; // Cast sRoot to a leaf

            if (currLeaf.Elements.contains(elem)) { // Case 4a -- Duplicate key
                Point coorElem = (Point) elem; // We know elem is a CoordinateEntry
                Point coorEntry = (Point) currLeaf.Elements.get(currLeaf.Elements.indexOf(elem)); // Get corresponding
                                                                                                  // entry

                // if current node is a leaf node and it has the elem => return null == can't
                // have that leaf node store anything
                if (coorEntry.getOffsetList().contains(coorElem.getOffsetList().get(0))) {
                    operationOk = false;
                } else {
                    coorEntry.addOffset(coorElem.getOffsetList().get(0));
                    operationOk = true;
                }
                return sRoot;

            } else if (currLeaf.Elements.size() >= BUCKET_CAPACITY) {
                sRoot = partition((PRQuadLeaf) sRoot, subArea); // Case 5 -- bucket is full
            } else {
                currLeaf.Elements.add(elem); // Case 4b -- Unique key
                operationOk = true;
                return sRoot;
            }
        }

        // Case 6 -- Current node is an internal node
        if (sRoot.getClass() == PRQuadInternal.class) {
            PRQuadInternal currInternal = (PRQuadInternal) sRoot;

            // Calculate quadrant that elem should go to and the center of this subregion
            Direction dir = elem.inQuadrant(subArea);
            long midX = subArea.getMidX();
            long midY = subArea.getMidY();

            // Calculate the bound for new subarea
            Area subAreaNW = new Area(subArea.getXLow(), midX, midY, subArea.getYHigh());
            Area subAreaNE = new Area(midX, subArea.getXHigh(), midY, subArea.getYHigh());
            Area subAreaSE = new Area(midX, subArea.getXHigh(), subArea.getYLow(), midY);
            Area subAreaSW = new Area(subArea.getXLow(), midX, subArea.getYLow(), midY);

            // Insert the new sub area to the right child which may lead to another split
            // recursively
            if (dir == Direction.NW) {
                currInternal.NW = insertHelper(currInternal.NW, elem, subAreaNW);
            } else if (dir == Direction.NE) {
                currInternal.NE = insertHelper(currInternal.NE, elem, subAreaNE);
            } else if (dir == Direction.SE) {
                currInternal.SE = insertHelper(currInternal.SE, elem, subAreaSE);
            } else if (dir == Direction.SW) {
                currInternal.SW = insertHelper(currInternal.SW, elem, subAreaSW);
            }
        }
        return sRoot;
    }

    /**
     * Partitions a leaf into an internal node
     *
     * @param nodeToPartition The node to partition
     * @param currentRegion   The current region that nodeToPartion sits in
     * @return A pointer to a new internal node
     */
    private PRQuadInternal partition(PRQuadLeaf node, Area currArea) {
        PRQuadInternal newInterNode = new PRQuadInternal();
        for (int i = 0; i < node.Elements.size(); i++) {
            Direction dir = node.Elements.get(i).inQuadrant(currArea);
            if (dir == Direction.NW) {
                if (newInterNode.NW == null) {
                    newInterNode.NW = new PRQuadLeaf();
                }
                ((PRQuadLeaf) newInterNode.NW).Elements.add(node.Elements.get(i));
            } else if (dir == Direction.NE) {
                if (newInterNode.NE == null) {
                    newInterNode.NE = new PRQuadLeaf();
                }
                ((PRQuadLeaf) newInterNode.NE).Elements.add(node.Elements.get(i));
            } else if (dir == Direction.SE) {
                if (newInterNode.SE == null) {
                    newInterNode.SE = new PRQuadLeaf();
                }
                ((PRQuadLeaf) newInterNode.SE).Elements.add(node.Elements.get(i));
            } else if (dir == Direction.SW) {
                if (newInterNode.SW == null) {
                    newInterNode.SW = new PRQuadLeaf();
                }
                ((PRQuadLeaf) newInterNode.SW).Elements.add(node.Elements.get(i));
            }
        }
        return newInterNode;
    }

    /**
     * Find a particular element in this PR Quad Tree
     *
     * @param elem The data to look for
     * @return A reference to an element x within the tree such that elem.equals(x)
     *         == true. Otherwise, returns null.
     */
    public T find(T elem) {
        if (elem != null) {
            PRQuadNode currNode = findHelper(root, elem, area);
            if (currNode != null) {
                for (int i = 0; i < ((PRQuadLeaf) currNode).Elements.size(); i++) {
                    if (((PRQuadLeaf) currNode).Elements.get(i).equals(elem)) {
                        return ((PRQuadLeaf) currNode).Elements.get(i);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Helper function to find data in a PR Quad Tree
     *
     * @param sRoot     The subroot for each recursive iteration
     * @param elem      The data of interest
     * @param subregion The subregion to narrow down for each recursive iteration
     * @return A reference to the node that contains the data. NULL if it cannot be
     *         found.
     */
    private PRQuadNode findHelper(PRQuadNode sRoot, T elem, Area subArea) {
        if (sRoot == null) { // if there is nothing then return nothing
            return null;
        } else if (sRoot.getClass() == PRQuadLeaf.class) {
            PRQuadLeaf currLeaf = (PRQuadLeaf) sRoot;
            if (currLeaf.Elements.contains(elem)) { // if sRooth is leaf then return node if found
                return sRoot;
            }
        } else if (sRoot.getClass() == PRQuadInternal.class) {
            PRQuadInternal currInternal = (PRQuadInternal) sRoot;

            // Calculate the direction of quadrant for element to got and the center of the
            // subregion
            Direction dir = elem.inQuadrant(subArea);
            long midX = subArea.getMidX();
            long midY = subArea.getMidY();

            // Calculate the bound for new subarea
            Area subAreaNW = new Area(subArea.getXLow(), midX, midY, subArea.getYHigh());
            Area subAreaNE = new Area(midX, subArea.getXHigh(), midY, subArea.getYHigh());
            Area subAreaSE = new Area(midX, subArea.getXHigh(), subArea.getYLow(), midY);
            Area subAreaSW = new Area(subArea.getXLow(), midX, subArea.getYLow(), midY);

            // Insert the new sub area to the right child which may lead to another split
            // recursively
            if (dir == Direction.NW) {
                return findHelper(currInternal.NW, elem, subAreaNW);
            } else if (dir == Direction.NE) {
                return findHelper(currInternal.NE, elem, subAreaNE);
            } else if (dir == Direction.SE) {
                return findHelper(currInternal.SE, elem, subAreaSE);
            } else if (dir == Direction.SW) {
                return findHelper(currInternal.SW, elem, subAreaSW);
            }
        }
        return null;
    }

    /**
     * Get a collection of references to all elements x such that x is in the tree
     * and x lies at coordinates within the defined rectangular region, including
     * the boundary of the region
     *
     * @param searchRegion The search region to use
     * @return An arraylist of T whose coordinates fit in the search region
     */
    public ArrayList<T> areaSearch(Area searchArea) {

        ArrayList<T> arr = new ArrayList<T>();
        if (area.checkIntersection(searchArea)) {
            // System.out.println("TESTING::AreaSearchHelper ");
            areaSearchHelper(this.root, arr, searchArea, area);
        }
        return arr;
    }

    /**
     * Traverse through a PR QuadTree, and populate a list given a search region
     *
     * @param sRoot        The subroot for each recursive iteration
     * @param list         The array list to modify
     * @param searchRegion The search region to use
     * @param subregion    The subregion to narrow down for each recursive iteration
     */
    private void areaSearchHelper(PRQuadNode sRoot, ArrayList<T> aList, Area searchArea, Area subArea) {
        if (sRoot == null) {
            return; // exit if there is nothing
        } else if (sRoot.getClass() == PRQuadLeaf.class) { // in case that ht leaf is in the search area
            PRQuadLeaf currLeaf = (PRQuadLeaf) sRoot;
            for (int i = 0; i < currLeaf.Elements.size(); i++) {
                if (currLeaf.Elements.get(i).inBox(searchArea)) {
                    aList.add(currLeaf.Elements.get(i));
                }
            }

        } else if (sRoot.getClass() == PRQuadInternal.class) {
            PRQuadInternal currInternal = (PRQuadInternal) sRoot;

            // Calculate the center of the current area
            long midX = (subArea.getXLow() + subArea.getXHigh()) / 2;
            long midY = (subArea.getYLow() + subArea.getYHigh()) / 2;

            // Calculate the bound for new subarea
            Area subAreaNW = new Area(subArea.getXLow(), midX, midY, subArea.getYHigh());
            Area subAreaNE = new Area(midX, subArea.getXHigh(), midY, subArea.getYHigh());
            Area subAreaSE = new Area(midX, subArea.getXHigh(), subArea.getYLow(), midY);
            Area subAreaSW = new Area(subArea.getXLow(), midX, subArea.getYLow(), midY);

            // Determine which region to go recursively
            if (searchArea.checkIntersection(subAreaNW)) {
                areaSearchHelper(currInternal.NW, aList, searchArea, subAreaNW);
            }
            if (searchArea.checkIntersection(subAreaNE)) {
                areaSearchHelper(currInternal.NE, aList, searchArea, subAreaNE);
            }
            if (searchArea.checkIntersection(subAreaSE)) {
                areaSearchHelper(currInternal.SE, aList, searchArea, subAreaSE);
            }
            if (searchArea.checkIntersection(subAreaSW)) {
                areaSearchHelper(currInternal.SW, aList, searchArea, subAreaSW);
            }
        }
    }

    /**
     * Writes a formatted display of the PRQuadTree's contents
     *
     * <pre>
     *     fw is open on an output file
     * </pre>
     *
     * @param fw The file writer to use for writing output
     */
    public void display(FileWriter fw) {
        try {
            if (root == null) {
                fw.write("Empty Tree\n");
            } else {
                displayHelper(fw, root, "");
            }
        } catch (IOException e) {
            System.err.println("Error: Can't open file to debug prQuadTree");
        }
    }

    /**
     * Helper function for displaying the PRQuadTree's contents
     *
     * @param fw      The file writer to use for writing output
     * @param sRoot   The subroot for each recursive iteration
     * @param padding The padding used to separate entries
     */
    private void displayHelper(FileWriter fw, PRQuadNode sRoot, String padding) {
        try {
            // Check for empty leaf
            if (sRoot == null) {
                fw.write(padding + "*\n");
                fw.flush();
                return;
            }

            // Check for and process SW and SE subtrees
            if (sRoot.getClass().equals(PRQuadInternal.class)) {
                PRQuadInternal p = (PRQuadInternal) sRoot;
                displayHelper(fw, p.SW, padding + "   ");
                displayHelper(fw, p.SE, padding + "   ");
            }

            // Determine if at leaf or internal and display accordingly
            if (sRoot.getClass().equals(PRQuadLeaf.class)) {
                PRQuadLeaf p = (PRQuadLeaf) sRoot;
                fw.write(padding);
                for (int pos = 0; pos < p.Elements.size(); pos++) {
                    fw.write(p.Elements.get(pos).toString() + " ");
                }
                fw.write("\n");
                fw.flush();
            } else if (sRoot.getClass().equals(PRQuadInternal.class)) {
                fw.write(padding + "@\n");
                fw.flush();
            }

            // Check for and process NE and NW subtrees
            if (sRoot.getClass().equals(PRQuadInternal.class)) {
                PRQuadInternal p = (PRQuadInternal) sRoot;
                displayHelper(fw, p.NE, padding + "   ");
                displayHelper(fw, p.NW, padding + "   ");
            }
        } catch (IOException exception) {
            System.err.println("Error: Can't open and write to output log file.");
        }
    }

    /**
     * Determines if an element could be inserted into this PRQuadTree's world
     * boundaries
     *
     * @param elem The element to insert
     * @return True if the element is within the boundaries. False if not
     */
    public boolean inBounds(T elem) {
        return elem.inBox(area);
    }
}
