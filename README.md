# GIS Virginia Parser Project - CS 3114

## Project Specification:
You will implement a system that indexes and provides search features for a file of GIS records, as described above.

Your system will build and maintain several in-memory index data structures to support these operations:
- Importing new GIS records into the database file
- Retrieving data for all GIS records matching given geographic coordinates
- Retrieving data for all GIS records matching a given feature name and state
- Retrieving data for all GIS records that fall within a given (rectangular) geographic region
- Displaying the in-memory indices in a human-readable manner

You will implement a single software system in Java to perform all system functions.

## Store & Search GIS Design:
![alt text](https://github.com/mnguyen0226/gis-virginia-parser/blob/main/imgs/store-and-search-gis.PNG) 

## Where is all the data structures implemented?
![alt text](https://github.com/mnguyen0226/gis-virginia-parser/blob/main/imgs/data-structures-locations.PNG)
## High Level GIS Design:
![alt text](https://github.com/mnguyen0226/gis-virginia-parser/blob/main/imgs/high-level-gis-code.PNG)

*Explanation:
## How to run?
First, unzip the submitted zip gis-virginia-parser file.

Second, add in the records files in the src/. (Already added)

Third, add in the command script log in src/scripts. (Already added)

Fourth, execute compilations in (next steps) and testing in (steps after).

*Compilation: All command below will be executed in the src/. Please follow all of them to avoid mis-compilation errors. Make sure to cd to src/.
- J4\src> javac .\gis\*.java	
- J4\src> javac .\gis\bufferpoolds\*.java
- J4\src> javac .\gis\hashtableds\*.java 
- J4\src> javac .\gis\hashtableds\Hash\*.java
- J4\src> javac .\gis\objectmodel\*.java  
- J4\src> javac .\gis\prquadtreeds\*.java

*Test: All command below will be executed in the src/. Make sure to cd to src/. Commands will create new databases, new output log files
- Script01: J4\src> java gis/GIS gis_db/db1.txt scripts/Script01.txt logs/log1.txt
- Script02: J4\src> java gis/GIS gis_db/db2.txt scripts/Script02.txt logs/log2.txt
- Script03: J4\src> java gis/GIS gis_db/db3.txt scripts/Script03.txt logs/log3.txt
- Script04: J4\src> java gis/GIS gis_db/db4.txt scripts/Script04.txt logs/log4.txt
- Script05: J4\src> java gis/GIS gis_db/db5.txt scripts/Script05.txt logs/log5.txt
- Script06: J4\src> java gis/GIS gis_db/db6.txt scripts/Script06.txt logs/log6.txt
- Script07: J4\src> java gis/GIS gis_db/db7.txt scripts/Script07.txt logs/log7.txt
- Script08: J4\src> java gis/GIS gis_db/db8.txt scripts/Script08.txt logs/log8.txt
- Script09: J4\src> java gis/GIS gis_db/db9.txt scripts/Script09.txt logs/log9.txt
- Script10: J4\src> java gis/GIS gis_db/db10.txt scripts/Script10.txt logs/log10.txt


## Lecture:
- (1) Hash Table: https://github.com/mnguyen0226/gis-virginia-parser/tree/main/lecture%20notes/hash-table
- (2) PR Quad Tree: https://github.com/mnguyen0226/gis-virginia-parser/tree/main/lecture%20notes/pr-quad-tree
- (3) Buffer Pool: https://github.com/mnguyen0226/gis-virginia-parser/tree/main/lecture%20notes/bufferpool
- (4) Design: https://github.com/mnguyen0226/gis-virginia-parser/tree/main/lecture%20notes/design%20lecture
## Paper:
H. Samet, “Storing a collection of polygons using quadtrees.” [Online]. Available: https://infolab.usc.edu/csci585/Spring2008/den_ar/p182-samet.pdf. [Accessed: 20-Apr-2021]. 

## Honor Code @VT Warning:
You know what VT's Honor Code is, Hokies :). Don't do it. You have been warned.

## Source Tree:
![alt text](https://github.com/mnguyen0226/gis-virginia-parser/blob/main/imgs/folder%20tree.PNG)