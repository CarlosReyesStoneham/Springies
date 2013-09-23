package control;

import jgame.platform.JGEngine;
import springies.BoardSetup;
import springies.EnvironmentForces;
import springies.Springies;
import walls.Wall;

/**
 * ControlWalls allows users to change the thickness of the walls by
 * pressing the up and down arrow keys. It could be used again if a user
 * wanted to change how the walls move or what keys are used.
 * @author carlosreyes and leevianagray
 */
public class ControlWalls {
	protected static final int WALLCHANGE = 10;
	private BoardSetup myBoardSetup;
	private Springies mySpringies;
	private EnvironmentForces myEnvForces;
	
	/** Constructor for control walls. */
	public ControlWalls(Springies mySpringies, BoardSetup myBoardSetup, EnvironmentForces myEnvForces) {
		this.mySpringies = mySpringies;
		this.myBoardSetup = myBoardSetup;
		this.myEnvForces = myEnvForces;
	}
	
	/** 
	 * wallIn is called in the Controls class in check user input.
	 * When the user presses the up arrow key, wallIn moves the bounding
	 * walls inward. Returns void.
	 */
	public void wallIn() {
		if (mySpringies.getKey(JGEngine.KeyUp) & BoardSetup.wall_margin < mySpringies.pfHeight()/2) {
			for (Wall w : myBoardSetup.getWalls()) {
				w.remove();
			}
			BoardSetup.changeMargin(WALLCHANGE);
			myBoardSetup.setWalls(myEnvForces.getWallMags(), myEnvForces.getWallMags());
			mySpringies.clearKey(JGEngine.KeyUp);
		}
	}
	
	/** 
	 * wallIn is called in the Controls class in check user input.
	 * When the user presses the down arrow key, wallIn moves the bounding
	 * walls outward. Returns void.
	 */
	public void wallOut() {
		if (mySpringies.getKey(JGEngine.KeyDown) & BoardSetup.wall_margin > 0) {
			for (Wall w : myBoardSetup.getWalls()) {
				w.remove();
			}
			BoardSetup.changeMargin(-1* WALLCHANGE);
			myBoardSetup.setWalls(myEnvForces.getWallMags(), myEnvForces.getWallMags());
			mySpringies.clearKey(JGEngine.KeyDown);
		}
	}
}
