Springies
=========
Authors: Leeviana Gray and Carlos Reyes

Part 1
Date started: 9/11/2013
Date finished: 9/16/2013

Part 2
Date started: 9/16/2013
Date finished: 9/22/2013


#Description#
Springies is a simple physics game. Springies imports
an XML file and reads in mass, muscle, fixed mass, and spring objects, attaching
them as specified in the XML. The environment also has properties such as
gravity, viscosity and wall repulsion, each of which are specified by an
optional XML file. If this file doesn't exist default values will be implemented.
When the springs are streched out they pull the mass objects attached to them towards
where the spring is pulled to. This pulling can be achieved by using the mouse to
click on a location in the playing field which will generate a spring between the mouse
and the nearest mass object. The mass objects will move around the playing field when
they are pulled. Variables in the environment, such as viscosity, gravity, center of mass,
wall repulsion, and bouncing off the walls will effect how these objects move.

#Loading in Files#
When opening springies, the user is prompted for the assembly XML file, this
file selection window can be reopened at anytime by pressing the N key. Multiple
assemblies can be loaded in. You can clear all the assemlies that were loaded in
by pressing the C key.

#Controls#
By pressing 'g', 'v', 'm', allow users to toggle (turn on if it is off and off if it is on) the application of
gravity, viscosity, and center of mass forces, respectively. By pressing '1', '2', '3', '4', allow users to 
toggle (turn on if it is off and off if it is on) the application of the appropriate wall repulsion forces. 
Otherwise default values are used. By pressing up arrow or down arrow, allow user to increase or decrease the 
size of the walled area in which the assemblies animate by 10 pixels on each side. This separates the area's 
size from that of the Canvas.

#Running#
Please set the path to the environment variables XML file and the desired object creation XML file
in the Springies.java class. Please run the program from Main.

#Known Bugs#
The walls hit the mass objects and push them away, thus enabling the shrinking of the box properly. However,
if the walls are moved inwards too quickly they can skip over the masses, leaving the masses out of the walled
boundaries.

