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
Springies is a simple physics game. Springies reads in assemblies through XML files that specify 
mass, muscle, fixed mass, and spring objects, arranged as specified in the XML. Objects in the springies environment are affected by
gravity, viscosity and wall repulsion, each of which can be modified in an
optional environment.xml file. If this file doesn't exist, default values will be implemented.
When the springs are stretched beyond their restlength, they pull the mass objects attached to them
"inwards" (towards the center of the spring). You can take advantage of this force by using the mouse to
click on a location in the playing and generate a spring between the mouse
and the nearest mass object. You can then drag your mouse around and the spring will apply force to the attached mass appropriately.

#Loading in Files#
When opening springies, the user is prompted for an assembly XML file, this
file selection window can be reopened at anytime by pressing the N key. Multiple
assemblies can be loaded in. You can clear all the assemlies that were loaded in
by pressing the C key.

#Controls#
By pressing 'g', 'v', 'm', you can toggle the application of
gravity, viscosity, and center of mass forces, respectively. By pressing '1', '2', '3', '4', you can toggle
the application of the appropriate wall repulsion forces (1 is the top and then the numbers travel clockwise).
By pressing the up arrow or down arrow, you can increase or decrease the 
size of the walled area in which the assemblies animate by 10 pixels on each side.

#Running#
Please set the path to the environment variables XML file and the desired object creation XML file
in the Springies.java class. Please run the program from Main.

#Known Bugs#
The walls hit the mass objects and push them away, thus enabling the shrinking of the box properly. However,
if the walls are moved inwards too quickly they can skip over the masses, leaving the masses out of the walled
boundaries.

