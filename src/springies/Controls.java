package springies;

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
	}
}
