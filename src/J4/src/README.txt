Name: Minh T. Nguyen
CS3114 - J4 README.txt
4/20/2021

-----------------------------------------------------------------------
(1)
Compilation: All command below will be executed in the src/. Please follow all of them to avoid mis-compilation errors. Make sure to cd to src/.
	J4\src> javac .\gis\*.java	
	J4\src> javac .\gis\bufferpoolds\*.java
	J4\src> javac .\gis\hashtableds\*.java 
	J4\src> javac .\gis\hashtableds\Hash\*.java
	J4\src> javac .\gis\objectmodel\*.java  
	J4\src> javac .\gis\prquadtreeds\*.java

-----------------------------------------------------------------------
(2)	
Element              Line    File
-----------------------------------------------------------------------
Hash table           46      J4/src/gis/hashtableds/Hash/hashTable.java
Hash table element   26      J4/src/gis/hashtableds/Hash/nameEntry.java

PR quadtree          29      J4/src/gis/prquadtreeds/PRQuadtree.java
PR quadtree element  32      J4/src/gis/prquadtreeds/Point.java

Buffer pool          30      J4/src/gis/bufferpoolds/BufferPool.java

GIS record           28      J4/src/gis/GIS.java

Feature name index   32      J4/src/gis/hashtableds/HashFeaNameIndex.java

Location index       32      J4/src/gis/prquadtreeds/PRQuadCoorIndex.java

-----------------------------------------------------------------------
(3)
Test: All command below will be executed in the src/. Make sure to cd to src/. Commands will create new databases, new output log files

* Script01: J4\src> java gis/GIS gis_db/db1.txt scripts/Script01.txt logs/log1.txt
* Script02: J4\src> java gis/GIS gis_db/db2.txt scripts/Script02.txt logs/log2.txt
* Script03: J4\src> java gis/GIS gis_db/db3.txt scripts/Script03.txt logs/log3.txt
* Script04: J4\src> java gis/GIS gis_db/db4.txt scripts/Script04.txt logs/log4.txt
* Script05: J4\src> java gis/GIS gis_db/db5.txt scripts/Script05.txt logs/log5.txt
* Script06: J4\src> java gis/GIS gis_db/db6.txt scripts/Script06.txt logs/log6.txt
* Script07: J4\src> java gis/GIS gis_db/db7.txt scripts/Script07.txt logs/log7.txt
* Script08: J4\src> java gis/GIS gis_db/db8.txt scripts/Script08.txt logs/log8.txt
* Script09: J4\src> java gis/GIS gis_db/db9.txt scripts/Script09.txt logs/log9.txt
* Script10: J4\src> java gis/GIS gis_db/db10.txt scripts/Script10.txt logs/log10.txt

-----------------------------------------------------------------------
(4)
Structures:					Description + Files contained

J4
└───src						- Home directory
    │   CO_All.txt
    │   NM_All.txt
    │   README.txt
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
    │   │   GIS.java
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
    ├───gis_db					- Customized Databases
    ├───logs					- My answer logs
    ├───ref_logs				- Answer log
    └───scripts					- Command script files
   
-----------------------------------------------------------------------