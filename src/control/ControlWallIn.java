package control;

import jgame.platform.JGEngine;
import springies.BoardSetup;
import springies.EnvironmentForces;
import springies.Springies;

/**
 * ControlWallIn move the walls on the playing field inward.
 * @author carlosreyes
 *
 */
public class ControlWallIn extends ControlWallMovement{

	public ControlWallIn(Springies mySpringies, BoardSetup myBoardSetup,
			EnvironmentForces myEnvForces) {
		super(mySpringies, myBoardSetup, myEnvForces);
	}

	@Override
	public void moveWall() {
		controlWalls(-1);
		mySpringies.clearKey(JGEngine.KeyDown);		
	}

}
