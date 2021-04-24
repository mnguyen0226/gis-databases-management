# GIS Virginia Parser Project - CS 3114

## Project Specification:


## Store & Search GIS Design:
![alt text](https://github.com/mnguyen0226/gis-virginia-parser/blob/main/design/store-and-search-gis.PNG) 

## High Level GIS Design:
![alt text](https://github.com/mnguyen0226/gis-virginia-parser/blob/main/design/high-level-gis-code.PNG)

## How to run?

## Lecture:
- (1) Hash Table: https://github.com/mnguyen0226/gis-virginia-parser/tree/main/lecture%20notes/hash-table
- (2) PR Quad Tree: https://github.com/mnguyen0226/gis-virginia-parser/tree/main/lecture%20notes/pr-quad-tree
- (3) Buffer Pool: https://github.com/mnguyen0226/gis-virginia-parser/tree/main/lecture%20notes/bufferpool
- (4) Design: https://github.com/mnguyen0226/gis-virginia-parser/tree/main/lecture%20notes/design%20lecture
## Paper:
H. Samet, “Storing a collection of polygons using quadtrees.” [Online]. Available: https://infolab.usc.edu/csci585/Spring2008/den_ar/p182-samet.pdf. [Accessed: 20-Apr-2021]. 

## Honor Code @VT Warning:
You know what VT's Honor Code is, Hokies :). Don't do it. You have been warned.

## Source Tree
J4
└──src						- Home directory
    │   CO_All.txt
    │   NM_All.txt
    │   Script01.txt
    │   sources.txt
    │   VA_All.txt
    │   VA_Bath.txt
    │   VA_Highland.txt
    │   VA_Monterey.txt
    │   VA_Montgomery.txt
    │
    ├───gis					- GIS code packages
    │   │   CommandParser.java       
    │   │   CommandProcessor.java    
    │   │   Controller.java
    │   │   DMSConverter.java        
    │   │   GIS.java				******** GIS.java ********* J4/src/gis/GIS.java
    │   │   GISRecordFileParser.java 
    │   │
    │   ├───bufferpoolds			- BufferPool Data Structure
    │   │       BufferPool.java      
    │   │
    │   ├───hashtableds				- Hashtable Data Structure
    │   │   │   HashFeaNameIndex.java
    │   │   │
    │   │   └───Hash
    │   │           Hashable.java
    │   │           hashTable.java
    │   │           nameEntry.java
    │   │
    │   ├───objectmodel				- Contain objects needed in the gis package 
    │   │       Area.java
    │   │       Command.java
    │   │       GISRecord.java
    │   │
    │   └───prquadtreeds			- PRQuadTree Data Structure 
    │           Compare2D.java
    │           Direction.java
    │           Point.java
    │           PRQuadCoorIndex.java
    │           PRQuadTree.java
    │
    ├───gis_db					- Customized Databases (will be filled when run the code)
    ├───logs					- My answer logs (will be filled when run the code)
    ├───ref_logs				- Answer log (empty - used to store TA's answer logs)
    └───scripts					- Command script files (empty - used to store TA's command scripts)