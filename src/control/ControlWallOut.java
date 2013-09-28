package control;

import jgame.platform.JGEngine;
import springies.BoardSetup;
import springies.EnvironmentForces;
import springies.Springies;

/**
 * ControlWallOut move the walls on the playing field outward.
 * @author carlosreyes
 *
 */
public class ControlWallOut extends ControlWallMovement{

	public ControlWallOut(Springies mySpringies, BoardSetup myBoardSetup,
			EnvironmentForces myEnvForces) {
		super(mySpringies, myBoardSetup, myEnvForces);
		
	}

	@Override
	public void moveWall() {
		controlWalls(1);
		mySpringies.clearKey(JGEngine.KeyUp);		
	}

}
