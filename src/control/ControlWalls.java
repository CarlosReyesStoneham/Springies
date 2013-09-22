package control;

import jgame.platform.JGEngine;
import springies.BoardSetup;
import springies.EnvironmentForces;
import springies.Springies;
import walls.Wall;

/*
 * Press up and down arrows to change wall thickness
 */
public class ControlWalls {
	protected static final int WALLCHANGE = 10;
	private BoardSetup myBoardSetup;
	private Springies mySpringies;
	private EnvironmentForces myEnvForces;
	
	public ControlWalls(Springies mySpringies, BoardSetup myBoardSetup, EnvironmentForces myEnvForces) {
		this.mySpringies = mySpringies;
		this.myBoardSetup = myBoardSetup;
		this.myEnvForces = myEnvForces;
	}
	
	public void wallIn() {
		if (mySpringies.getKey(JGEngine.KeyUp)) {
			for (Wall w : myBoardSetup.getWalls()) {
				w.remove();
				w.setThickness(WALLCHANGE);
			}
			BoardSetup.wall_margin += WALLCHANGE;
			myBoardSetup.setWalls(myEnvForces.getWallMags(), myEnvForces.getWallMags());
			mySpringies.clearKey(JGEngine.KeyUp);
		}
	}
	
	public void wallOut() {
		if (mySpringies.getKey(JGEngine.KeyDown)) {
			for (Wall w : myBoardSetup.getWalls()) {
				w.remove();
				w.setThickness(-WALLCHANGE);
			}
			BoardSetup.wall_margin -= WALLCHANGE;
			myBoardSetup.setWalls(myEnvForces.getWallMags(), myEnvForces.getWallMags());
			mySpringies.clearKey(JGEngine.KeyDown);
		}
	}
}
