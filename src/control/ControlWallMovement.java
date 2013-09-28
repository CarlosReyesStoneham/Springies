package control;

import springies.BoardSetup;
import springies.EnvironmentForces;
import springies.Springies;
import walls.Wall;

/**
 * ControlWalls allows users to change the thickness of the walls by
 * pressing the up and down arrow keys. It could be used again if a user
 * wanted to change how the walls move or what keys are used.
 * @author carlosreyes
 */
public abstract class ControlWallMovement {
	protected static final int WALLCHANGE = 10;
	private BoardSetup myBoardSetup;
	protected Springies mySpringies;
	private EnvironmentForces myEnvForces;
	
	/** Constructor for control walls. */
	public ControlWallMovement(Springies mySpringies, BoardSetup myBoardSetup, EnvironmentForces myEnvForces) {
		this.mySpringies = mySpringies;
		this.myBoardSetup = myBoardSetup;
		this.myEnvForces = myEnvForces;
	}
	
	/**
	 * Control wall is a helper method for moveWall.
	 */
	public void controlWalls(int moveFactor) {
		for (Wall w : myBoardSetup.getWalls()) {
			w.remove();
		}
		BoardSetup.changeMargin(moveFactor*WALLCHANGE);
		myBoardSetup.setWalls(myEnvForces.getWallMags(), myEnvForces.getWallMags());

	}
	
	/** 
	 * moveWall is called in the Controls class in check user input.
	 * When the user presses the up arrow key, wallIn moves the bounding
	 * walls inward, when the user presses the down arrow key the bounding
	 * walls shrink inward.
	 */	
	public abstract void moveWall();
}
