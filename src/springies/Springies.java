package springies;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import jboxGlue.Mass;
import jboxGlue.MovableMass;
import jboxGlue.Spring;
import jboxGlue.PhysicalObject;
import jboxGlue.Wall;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.platform.JGEngine;
import org.jbox2d.common.Vec2;
import springies.XMLReader;

@SuppressWarnings("serial")
public class Springies extends JGEngine {

	// Array of massLists
	ArrayList<HashMap<String, Mass>> massMaps = new ArrayList<HashMap<String, Mass>>();

	// Array of Springs... only used for clearing :\
	ArrayList<ArrayList<Spring>> springArrays = new ArrayList<ArrayList<Spring>>();

	// TODO: We should make an environment
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
		int height = 600;
		double aspect = 16.0 / 9.0;
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
		setFrameRate(60, 2);

		WorldManager.initWorld(this);

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

		makeAssembly();

		File f = new File("src/springies/environment.xml");
		if (f.exists()) {
			XMLReader env = new XMLReader("src/springies/environment.xml");

			gravDir = env.readGravity()[0];
			gravMag = env.readGravity()[1] * .00001; // Adjusting gravity
			viscosity = env.readViscosity();
			cmMag = env.readcm()[0];
			cmExp = env.readcm()[1];
			wallMag = env.readWallMag();
			wallExp = env.readWallExp();
		} else {
			gravDir = 0;
			gravMag = 0;
			viscosity = 1;
			cmMag = 0;
			cmExp = 0;
			wallMag = new double[4];
			wallExp = new double[4];
		}
	}

	@Override
	public void doFrame() {
		checkUserInput();
		// set gravity... I'm going to assume that 0 is normal and that it goes
		// clockwise

		WorldManager.getWorld().setGravity(
				new Vec2((float) (gravMag * Math.cos(90)),
						(float) (gravMag * Math.sin(gravDir))));

		// update game objects
		WorldManager.getWorld().step(1f, 1);
		moveObjects();
		checkCollision(2, 1);

		for (HashMap<String, Mass> massList : massMaps) {
			for (Mass m : massList.values()) {
				// walls repel
				for (int i = 0; i < 4; i++) {
					// 0 is top wall, 1 is right etc.
					m.applyForce(new Vec2(0, (float) (wallMag[0] / Math.pow(
							m.y, wallExp[0]))));
					m.applyForce(new Vec2((float) (wallMag[1] / Math.pow(
							-pfWidth() - m.x, wallExp[1])), 0));
					m.applyForce(new Vec2(0, (float) (wallMag[2] / Math.pow(
							-pfHeight() - m.y, wallExp[2]))));
					m.applyForce(new Vec2((float) (wallMag[3] / Math.pow(m.x,
							wallExp[3])), 0));
				}

				// viscosity - resistive force on masses proportional to their
				// velocity
				m.yspeed = m.yspeed * viscosity;
				m.xspeed = m.xspeed * viscosity;

				/*
				 * for(MovableMass otherMass : massList){ Vec2 cmForce = new
				 * Vec2((float) (cmMag * Math.pow(m.x - otherMass.x,cmExp)),
				 * (float) ( cmMag * Math.pow(m.y - otherMass.y,cmExp)));
				 * otherMass.applyForce(cmForce); }
				 */
			}
		}
	}

	private void checkUserInput() {
		if (getKey('N')) {
			clearKey('N');
			makeAssembly();
		}
		if (getKey('C')) {
			// removeObjects doesn't work for some reason (use debugger to find
			// out later?)
			clearKey('C');

			for (ArrayList<Spring> springArray : springArrays) {
				for (Spring s : springArray) {
					s.remove();
				}
			}
			
			for (HashMap<String, Mass> massList : massMaps) {
				for (Mass m : massList.values()) {
					m.remove();
				}
			}

			massMaps.clear();
			springArrays.clear();
		}
	}

	private void makeAssembly() {
		FileDialog selector = new FileDialog(new Frame());
		selector.setVisible(true);
		XMLReader reader = new XMLReader("src/springies/" + selector.getFile());
		massMaps.add(reader.makeMasses());
		springArrays.add(reader.makeSprings());
		springArrays.add(reader.makeMuscles());
	}

	public void centerOfMass() {
		float topX = 0;
		float topY = 0;
		float totalMass = 500;
		// TODO: where are you using cmMag and cmExp?
		for (HashMap<String, Mass> massList : massMaps) {
			for (Mass m : massList.values()) {
				topX += (float) (m.getMyMass() * m.x);
				// System.out.println(topX);
				topY += (float) (m.getMyMass() * m.y);
			}
		}

		float xCoord = (float) (topX / totalMass);
		float yCoord = (float) (topY / totalMass);
		new Mass(xCoord, yCoord, 1);
	}

	@Override
	public void paintFrame() {

	}
}
