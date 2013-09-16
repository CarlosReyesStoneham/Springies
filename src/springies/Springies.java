package springies;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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

	HashMap<String, Mass> massList = new HashMap<String, Mass>();

	// we should probably make an environment class if we have time. Also TODO:
	// change a lot of these to floats so I don't need to keep changing the type
	// when creating Vec2s
	double gravDir;
	double gravMag;
	double viscosity;
	double cmMag;
	double cmExp;
	double[] wallMag = new double[4];
	double[] wallExp = new double[4];

	public Springies() {
		// set the window size
		// int height = 480; //original size
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

		// Sets the gravity
		// WorldManager.getWorld().setGravity(new Vec2(0.0f, 0.1f));
		// Controls a = new Controls(this, 0.0f, 0.0f);
		// WorldManager.getWorld().setGravity(new Vec2(a.xGravity, a.yGravity));
		// System.out.println(a.xGravity);

		// need to get force from velocity. I think it involves taking the
		// square root

		// massList.add(new MovableMass(displayWidth() / 2 + 100,
		// displayHeight() / 2, 8000, -10000, 5));
		// massList.add(new MovableMass(displayWidth() / 2, displayHeight() / 2,
		// -5000, 10000, 5));
		// new Muscle(massList.get(0), massList.get(1), 300, .005, 2);
		// new Mass(displayWidth() / 2 - 100, displayHeight() / 2, 1);

		// add walls to bounce off of
		// NOTE: immovable objects must have no mass
		final double WALL_MARGIN = 10;
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = displayWidth() - WALL_MARGIN * 2
				+ WALL_THICKNESS;
		final double WALL_HEIGHT = displayHeight() - WALL_MARGIN * 2
				+ WALL_THICKNESS;
		PhysicalObject wall = new Wall("wall", 2, JGColor.green, WALL_WIDTH,
				WALL_THICKNESS);
		wall.setPos(displayWidth() / 2, WALL_MARGIN);
		wall = new Wall("wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS);
		wall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);
		wall = new Wall("wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT);
		wall.setPos(WALL_MARGIN, displayHeight() / 2);
		wall = new Wall("wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT);
		wall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);

		XMLReader reader = new XMLReader("src/springies/jello.xml");
		massList = reader.makeMasses();
		reader.makeSprings();
	//	reader.makeMuscles();
		File f = new File("src/springies/environment.xml");
		if(f.exists()){
			XMLReader env = new XMLReader("src/springies/environment.xml");
			
			gravDir = env.readGravity()[0];
			gravMag = env.readGravity()[1];
			viscosity = env.readViscosity();
			cmMag = env.readcm()[0];
			cmExp = env.readcm()[1];
			wallMag = env.readWallMag();
			wallExp = env.readWallExp();	
		}
		else{
			gravDir = 0;
			gravMag = 20;
			viscosity = 1;
			cmMag = 0;
			cmExp = 0;
			wallMag = new double[4];
			wallExp = new double[4];
		}
	}

	Controls gravControl = new Controls(this, 0.0f, 0.0f);

	@Override
	public void doFrame() {
		// set gravity... I'm going to assume that 0 is normal and that it goes
		// clockwise
		
		// multiplying by gravMag makes gravity waaaaay too strong... um, not
		// sure what the magnitude of gravity is even supposed to MEAN anyways.
		// Physically nonsensical.
		
		WorldManager.getWorld().setGravity(new Vec2(0,0));/*
				new Vec2((float) (Math.cos(90)), (float) (Math.sin(gravDir))));
		*/
		//gravControl.changeGravity();
		
		// update game objects
		WorldManager.getWorld().step(1f, 1);
		moveObjects();
		checkCollision(2, 1);

		
		for (Mass m : massList.values()) {
			// walls repel
			for (int i = 0; i < 4; i++) {
				// 0 is top wall, 1 is right etc.
				m.applyForce(new Vec2(0, (float) (wallMag[0] / Math.pow(m.y,
						wallExp[0]))));
				m.applyForce(new Vec2((float) (wallMag[1] / Math.pow(-pfWidth()
						- m.x, wallExp[1])), 0));
				m.applyForce(new Vec2(0, (float) (wallMag[2] / Math.pow(
						-pfHeight() - m.y, wallExp[2]))));
				m.applyForce(new Vec2((float) (wallMag[3] / Math.pow(m.x,
						wallExp[3])), 0));
			}
			

			// viscosity - resistive force on masses proportional to their
			// velocity
			m.yspeed = m.yspeed * viscosity;
			m.xspeed = m.xspeed * viscosity;

			// center of mass... uh? there has to be an easy jbox way to
			// implement this. Also, this doesn't make any sense in the physics
			// sense.
			/*
			 * for(MovableMass otherMass : massList){ Vec2 cmForce = new
			 * Vec2((float) (cmMag * Math.pow(m.x - otherMass.x,cmExp)), (float)
			 * ( cmMag * Math.pow(m.y - otherMass.y,cmExp)));
			 * otherMass.applyForce(cmForce); }
			 */
		}
	
	}

	@Override
	public void paintFrame() {
		// nothing to do
		// the objects paint themselves
	}
}
