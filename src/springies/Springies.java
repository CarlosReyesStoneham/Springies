package springies;

import java.util.ArrayList;
import jboxGlue.Mass;
import jboxGlue.MovableMass;
import jboxGlue.PhysicalObject;
import jboxGlue.Spring;
import jboxGlue.Wall;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.platform.JGEngine;
import org.jbox2d.common.Vec2;

@SuppressWarnings("serial")
public class Springies extends JGEngine {
	public Springies() {
		// set the window size
		int height = 480;
		double aspect = 16.0 / 9.0;
		initEngine((int) (height * aspect), height);
	}

	@Override
	public void initCanvas() {
		// I have no idea what tiles do...
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
		setFrameRate(60, 2);

		// init the world
		// One thing to keep straight: The world coordinates have y pointing
		// down
		// the game coordinates have y pointing up
		// so gravity is along the positive y axis in world coords to point down
		// in game coords
		// remember to set all directions (eg forces, velocities) in world
		// coords
		WorldManager.initWorld(this);
		WorldManager.getWorld().setGravity(new Vec2(0.0f, 0.1f));

		// need to get force from velocity. I think it involves taking the
		// square root

		ArrayList<Mass> Masses = new ArrayList<Mass>();

		Masses.add(new MovableMass(displayWidth() / 2 + 100,
				displayHeight() / 2, 8000, -10000, 5));
		Masses.add(new MovableMass(displayWidth() / 2, displayHeight() / 2,
				-5000, 10000, 5));
		new Spring(Masses.get(0), Masses.get(1), 300, .005);
		new Mass(displayWidth() / 2 - 100, displayHeight() / 2, 1);

		// add walls to bounce off of
		// NOTE: immovable objects must have no mass
		final double WALL_MARGIN = 10;
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = displayWidth() - WALL_MARGIN * 2
				+ WALL_THICKNESS;
		final double WALL_HEIGHT = displayHeight() - WALL_MARGIN * 2
				+ WALL_THICKNESS;
		PhysicalObject wall = new Wall("wall", 2, JGColor.green,
				WALL_WIDTH, WALL_THICKNESS);
		wall.setPos(displayWidth() / 2, WALL_MARGIN);
		wall = new Wall("wall", 2, JGColor.green, WALL_WIDTH,
				WALL_THICKNESS);
		wall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);
		wall = new Wall("wall", 2, JGColor.green, WALL_THICKNESS,
				WALL_HEIGHT);
		wall.setPos(WALL_MARGIN, displayHeight() / 2);
		wall = new Wall("wall", 2, JGColor.green, WALL_THICKNESS,
				WALL_HEIGHT);
		wall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);
	}

	@Override
	public void doFrame() {
		// update game objects
		WorldManager.getWorld().step(1f, 1);
		moveObjects();
		checkCollision(2, 1);
	}

	@Override
	public void paintFrame() {
		// nothing to do
		// the objects paint themselves
	}
}
