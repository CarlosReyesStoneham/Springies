package springies;

import java.util.HashMap;
import jboxGlue.Mass;
import jboxGlue.PointMass;
import jboxGlue.Spring;
import jboxGlue.Wall;
import jgame.platform.JGEngine;

public class Controls {
	
	private static final int WALLCHANGE = 10;
	private static final int LARGENUM = 9999;
	private static final int NUMKEYS = 4;
	
	private static final String COM = "COM";
	private static final String GRAV = "Gravity";
	private static final String VISC = "Viscosity";
	
	private static final char N = 'N';
	private static final char C = 'C';
	private static final char G = 'G';
	private static final char V = 'V';
	private static final char M = 'M';

	
	protected Springies mySpringies;
	private EnvironmentForces myEnvForces;
	private BoardSetup myBoardSetup;
	protected PointMass myMouseMass;
	private Spring myMouseSpring;

	public Controls(Springies springies, EnvironmentForces envForces) {
		this.mySpringies = springies;
		this.myBoardSetup = new BoardSetup(springies, envForces.getWallMags(),
				envForces.getWallMags());
		this.myEnvForces = envForces;
	}
	
	//MouseClick mClick = new MouseClick(mySpringies, myMouseMass, myMouseSpring);

	public void checkUserInput() {
		// Press 'N' to load new assembly
		if (mySpringies.getKey(N)) {
			mySpringies.clearKey(N);
			myBoardSetup.makeAssembly();
		}

		// Press 'C' to clear assemblies
		if (mySpringies.getKey(C)) {
			mySpringies.clearKey(C);

			mySpringies.removeObjects("Spring", 0);
			mySpringies.removeObjects("Mass", 0);

			mySpringies.clearMassMaps();
		}

		// Press G, V, or M to toggle Gravity, Viscosity or CenterOfMass forces
		HashMap<Character, String> forces = new HashMap<Character, String>();
		forces.put(G, GRAV);
		forces.put(V, VISC);
		forces.put(M, COM);

		for (char c : forces.keySet()) {
			if (mySpringies.getKey(c)) {
				mySpringies.clearKey(c);
				myEnvForces.toggle(forces.get(c));
			}
		}

		// Press up and down arrows to change wall thickness
		if (mySpringies.getKey(JGEngine.KeyUp)) {
			for (Wall w : myBoardSetup.getWalls()) {
				w.remove();
				w.setThickness(WALLCHANGE);
			}
			BoardSetup.wall_margin += WALLCHANGE;
			myBoardSetup.setWalls(myEnvForces.getWallMags(), myEnvForces.getWallMags());
			mySpringies.clearKey(JGEngine.KeyUp);
		}

		if (mySpringies.getKey(JGEngine.KeyDown)) {
			for (Wall w : myBoardSetup.getWalls()) {
				w.remove();
				w.setThickness(-WALLCHANGE);
			}
			BoardSetup.wall_margin -= WALLCHANGE;
			myBoardSetup.setWalls(myEnvForces.getWallMags(), myEnvForces.getWallMags());
			mySpringies.clearKey(JGEngine.KeyDown);
		}

		// Press 1, 2, 3, 4 (not on numpad) to toggle wall forces
		char[] possibleWalls = { '1', '2', '3', '4' };
		for (int i = 0; i < NUMKEYS; i++) {
			if (mySpringies.getKey(possibleWalls[i])) {
				mySpringies.clearKey(possibleWalls[i]);
				myBoardSetup.getWalls()[i].toggleWallForce();
			}
		}

		// Click to make temporary spring
		
		if (mySpringies.getMouseButton(1)) {
			if (myMouseMass == null) {
				double shortestPath = LARGENUM;
				Mass closestMass = null;
				int x = mySpringies.getMouseX();
				int y = mySpringies.getMouseY();
				myMouseMass = new PointMass(mySpringies, mySpringies.pfWidth()
						- x, mySpringies.pfHeight() - y, 1);
				myMouseMass.x = x;
				myMouseMass.y = y;

				for (HashMap<String, Mass> massList : mySpringies.getMassMaps()) {
					for (Mass mass : massList.values()) {
						double len = calculateLength(mass, myMouseMass);
						if (len < shortestPath) {
							shortestPath = len;
							closestMass = mass;
						}
					}
				}

				myMouseSpring = new Spring(myMouseMass, closestMass);
			}
		} else {
			if (myMouseMass != null) {
				myMouseMass.remove();
				myMouseSpring.remove();
				myMouseMass = null;
			}
		}

	}

	protected double calculateLength(Mass one, Mass two) {
		return Math.sqrt(Math.pow(one.x - two.x, 2)
				+ Math.pow(one.y - two.y, 2));
	}
}
