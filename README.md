Springies
=========
Authors: Leeviana Gray and Carlos Reyes
Date started: 9/11/2013
Date finished: 9/16/2013

Implementation one of the the springies physics simulation. 

Springies is a simple physics game. Springies imports
an XML file and reads in mass, muscle, fixed mass, and spring objects, attaching
them as specified in the XML. The environment also has properties such as
gravity, viscosity and wall repulsion, each of which are specified by an
optional XML file. If this file doesn't exist default values will be implemented

Environment manipulations via environment.xml file:
gravity - magnitude and exponent
viscosity - magnitude
centermass - magnitude and exponent
wall - magnitude and exponent for 4 walls (wall id 1 is the top wall and it goes counterclockwise from there)


Running the program:
Please set the path to the environment variables XML file and the desired object creation XML file
in the Springies.java class. Please run the program from Main.

Known Bugs: There is currently a bug with the repulsion method, the center of mass is not being put
in the right place, additionally, the center of mass neither repels nor attracts anything. 
