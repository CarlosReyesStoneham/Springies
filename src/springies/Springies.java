package springies;

import java.util.ArrayList;
import java.util.HashMap;

import control.Controls;
import jboxGlue.Mass;
import jboxGlue.WorldManager;
import jgame.platform.JGEngine;

/**
 * Springies is the object created that runs right
 * below the main class.
 * @author carlosreyes and leevianagray
 */
@SuppressWarnings("serial")
public class Springies extends JGEngine {

	private static final double ASPECTW = 16.0;
	private static final double ASPECTH = 9.0;
	private static final int HEIGHT = 600;
	private static final int FRAMERATE = 60;
	private static final int FRAMESKIP = 2;

	// Master Array of massLists
	ArrayList<HashMap<String, Mass>> massMaps = new ArrayList<HashMap<String, Mass>>();

	EnvironmentForces myEnvForce;
	Controls myController;

	public Springies() {
		int height = HEIGHT;
		double aspect = ASPECTW / ASPECTH;
		initEngine((int) (height * aspect), height);
	}

	@Override
	public void initCanvas() {
		setCanvasSettings(1, // width of the canvas in tiles
				1, // height of the canvas in tiles
				displayWidth(), // width of one tile
				displayHeight(), // height of one tile
				null,// foreground colour -> use default colour white
				null,// background colour -> use default colour black
				null // standard font -> use default font
		);
	}

	@Override
	public void initGame() {
		setFrameRate(FRAMERATE, FRAMESKIP);
		WorldManager.initWorld(this);
		myEnvForce = new EnvironmentForces(this);
		myController = new Controls(this, myEnvForce);
	}

	@Override
	public void doFrame() {
		// dbgShowBoundingBox(true);
		myController.checkUserInput();
		myEnvForce.doForces();

		WorldManager.getWorld().step(1f, 1);
		moveObjects();
		checkCollision(2, 1);
	}

	public ArrayList<HashMap<String, Mass>> getMassMaps() {
		return massMaps;
	}

	public void addMassMap(HashMap<String, Mass> map) {
		massMaps.add(map);
	}

	public void clearMassMaps() {
		massMaps.clear();
	}
}
