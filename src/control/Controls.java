package control;

import java.util.HashMap;

import springies.BoardSetup;
import springies.EnvironmentForces;
import springies.Springies;

import jboxGlue.PointMass;
import jboxGlue.Spring;

/**
 * Controls is the where methods that utilize any kind of user input are called.
 * An example of where controls would be used would be any time when someone would
 * like to add another set of user input such as adding arrow key so that when a 
 * mass is clicked it could be moved around with these keys. Controls would be extended
 * and these keys would be added as methods to the child class, then these methods would
 * be called in the checkUserInput class of the Controls class.
 * @author carlosreyes and leevianagray
 */
public class Controls {
	protected static final int WALLCHANGE = 10;
	private static final int NUMKEYS = 4;

	private static final String COM = "COM";
	private static final String GRAV = "Gravity";
	private static final String VISC = "Viscosity";

	private static final char G = 'G';
	private static final char V = 'V';
	private static final char M = 'M';

	protected Springies mySpringies;
	protected EnvironmentForces myEnvForces;
	private BoardSetup myBoardSetup;
	private PointMass myMouseMass;
	private Spring myMouseSpring;
	private MouseClick mClick;
	
	/** The constructor for the Controls class. */
	public Controls(Springies springies, EnvironmentForces envForces) {
		this.mySpringies = springies;
		this.myBoardSetup = new BoardSetup(springies, envForces.getWallMags(),
				envForces.getWallMags());
		this.myEnvForces = envForces;
		mClick = new MouseClick(mySpringies, myMouseMass, myMouseSpring);
	}
	
	/** 
	 * The checkUserInput is called within the game loop, thus is is called
	 * often to check for any user input. Any new control functionality must be
	 * fed through checkUserInput.
	 */
	public void checkUserInput() {
		ControlLoading controlLoad = new ControlLoading(mySpringies, myBoardSetup);
		ControlWalls controlWalls = new ControlWalls(mySpringies, myBoardSetup,
				myEnvForces);

		// Press 'N' to load new assembly
		controlLoad.load();
		// Press 'C' to clear assemblies
		controlLoad.clear();

		// toggle gravity, viscocity, and center of mass
		toggleGVM();

		// Press up and down arrows to change wall thickness
		controlWalls.wallIn();
		controlWalls.wallOut();
		
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
	
	/**
	 * ToggleGVM stands for toggle Gravity, Viscosity, and Center of Mass.
	 * This method simply turns each of these environmental forces on or off.
	 * If another environmental force is added, it may be called in the
	 * checkUserInput method, however it is highly recommended that it is called
	 * here. Press G, V, or M to toggle Gravity, Viscosity or CenterOfMass
	 * forces.
	 */
	private void toggleGVM() {
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
}
