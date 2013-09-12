package springies;

import java.util.ArrayList;
import jboxGlue.Mass;
import jboxGlue.MovableMass;
import jboxGlue.Muscle;
import jboxGlue.PhysicalObject;
import jboxGlue.Spring;
import jboxGlue.Wall;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.platform.JGEngine;
import org.jbox2d.common.Vec2;
import springies.XMLReader;

@SuppressWarnings("serial")
public class Springies extends JGEngine {
	
	public Springies() {
		// set the window size
		//int height = 480; //original size
		int height = 600;
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
		
		//Sets the gravity
		//WorldManager.getWorld().setGravity(new Vec2(0.0f, 0.1f));
//		Controls a = new Controls(this, 0.0f, 0.0f);
//		WorldManager.getWorld().setGravity(new Vec2(a.xGravity, a.yGravity));
//		System.out.println(a.xGravity);
		
		// need to get force from velocity. I think it involves taking the
		// square root

		ArrayList<Mass> massList = new ArrayList<Mass>();

		massList.add(new MovableMass(displayWidth() / 2 + 100,
				displayHeight() / 2, 8000, -10000, 5));
		massList.add(new MovableMass(displayWidth() / 2, displayHeight() / 2,
				-5000, 10000, 5));
		new Muscle(massList.get(0), massList.get(1), 300, .005, 2);
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
		
		XMLReader reader = new XMLReader();
		reader.getMass();
		reader.getSpring();
	}
	
	Controls gravControl = new Controls(this, 0.0f, 0.0f);

	@Override
	public void doFrame() {
		
		WorldManager.getWorld().setGravity(new Vec2(gravControl.xGravity, gravControl.yGravity));
		gravControl.changeGravity();
		System.out.println(gravControl.xGravity);
		System.out.println(gravControl.yGravity);
		
		// update game objects
		WorldManager.getWorld().step(1f, 1);
		moveObjects();
		checkCollision(2, 1);
		
	/*	for(Mass m : massList){
			
		}*/
	}

	@Override
	public void paintFrame() {
		// nothing to do
		// the objects paint themselves
	}
}
