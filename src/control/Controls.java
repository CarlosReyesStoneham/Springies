package control;

import java.util.HashMap;

import springies.BoardSetup;
import springies.EnvironmentForces;
import springies.Springies;


import jboxGlue.Mass;
import jboxGlue.PointMass;
import jboxGlue.Spring;
import jboxGlue.Wall;
import jgame.platform.JGEngine;

public class Controls {
	
	protected static final int WALLCHANGE = 10;
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
	protected EnvironmentForces myEnvForces;
	private BoardSetup myBoardSetup;
	protected PointMass myMouseMass;
	private Spring myMouseSpring;

	public Controls(Springies springies, EnvironmentForces envForces) {
		this.mySpringies = springies;
		this.myBoardSetup = new BoardSetup(springies, envForces.getWallMags(),
				envForces.getWallMags());
		this.myEnvForces = envForces;
		mClick = new MouseClick(mySpringies, myMouseMass, myMouseSpring);

	}
	
	private MouseClick mClick;

	public void checkUserInput() {
		ControlLoading ca = new ControlLoading(mySpringies, myBoardSetup);
		ControlWalls cw = new ControlWalls(mySpringies, myBoardSetup, myEnvForces);

		// Press 'N' to load new assembly
		ca.load();
		// Press 'C' to clear assemblies
		ca.clear();
		
		//toggle gravity, viscocity, and center of mass
		toggleGVM();
		
		// Press up and down arrows to change wall thickness
		cw.wallIn();
		cw.wallOut();


		// Press 1, 2, 3, 4 (not on numpad) to toggle wall forces
		char[] possibleWalls = { '1', '2', '3', '4' };
		for (int i = 0; i < NUMKEYS; i++) {
			if (mySpringies.getKey(possibleWalls[i])) {
				mySpringies.clearKey(possibleWalls[i]);
				myBoardSetup.getWalls()[i].toggleWallForce();
			}
		}

		// Click to make temporary spring
		mClick.click();

	}
	
	private void toggleGVM() {
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
		
	}
	
	protected double calculateLength(Mass one, Mass two) {
		return Math.sqrt(Math.pow(one.x - two.x, 2)
				+ Math.pow(one.y - two.y, 2));
	}
}
