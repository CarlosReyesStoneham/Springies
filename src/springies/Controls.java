package springies;

import java.util.HashMap;

import jboxGlue.Mass;
import jboxGlue.PointMass;
import jboxGlue.Spring;
import jboxGlue.Wall;
import jgame.platform.JGEngine;

public class Controls {
	int initialArea = 10; // Initial wall margin

	private Springies springies;
	private BoardSetup boardSetup;

	public Controls(Springies springies, BoardSetup boardSetup) {
		this.springies = springies;
		this.boardSetup = boardSetup;
	}
	public void checkUserInput() {
		// Press 'N' to load new assembly
		if (springies.getKey('N')) {
			springies.clearKey('N');
			boardSetup.makeAssembly();
		}
		// Press 'C' to clear assemblies
		if (springies.getKey('C')) {
			springies.clearKey('C');

			springies.removeObjects("Spring", 0);
			springies.removeObjects("Mass", 0);

			springies.clearMassMaps();
		}

		// Press 'G' to toggle gravity
		if (springies.getKey('G')) {
			if (springies.toggleGravity == 1) {
				springies.toggleGravity = 0;
			} else {
				springies.toggleGravity = 1;
			}
		}

		// Press up and down arrows to change wall thickness
		if (springies.getKey(JGEngine.KeyUp)) {
			for(Wall w: boardSetup.getWalls()){
				w.setThickness(10);
			}
		}

		if (springies.getKey(JGEngine.KeyDown)) {
			for(Wall w: boardSetup.getWalls()){
				w.setThickness(-10);
			}
		}

		if (springies.getMouseButton(1)){
			double shortestPath = 999999;
			Mass closestMass = null;
			int x = springies.getMouseX();
			int y = springies.getMouseY();
			PointMass mouse = new PointMass(springies.pfWidth()-x, springies.pfHeight()-y, 1);
			mouse.x = x;
			mouse.y = y;
			
			for (HashMap<String, Mass> massList : springies.getMassMaps()) {
				for (Mass mass : massList.values()) {

					double len = calculateLength(mass, mouse);
					if (len < shortestPath) {
						System.out.println(mass.x);
						System.out.println(mouse.x);
						shortestPath = len;
						closestMass = mass;
					}
				}
			
			}
			
			new Spring(mouse, closestMass, shortestPath, 1);
			springies.clearMouseButton(1);
		}
		
	}
	private double calculateLength(Mass one, Mass two) {
		return Math.sqrt(Math.pow(one.x - two.x, 2)
				+ Math.pow(one.y - two.y, 2));
	}
}
