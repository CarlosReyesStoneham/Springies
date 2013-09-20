package springies;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import jboxGlue.Mass;
import jboxGlue.Spring;
import jboxGlue.Wall;
import jgame.JGColor;

public class BoardSetup {
	double gravDir;
	double gravMag;
	double viscosity;
	double cmMag;
	double cmExp;
	double[] wallMag = new double[4];
	double[] wallExp = new double[4];
	
	private ArrayList<Wall> wallList;
	public Springies springies;
	
	public BoardSetup(Springies springies, ArrayList<Wall> wallList) {
		this.springies = springies;
		this.wallList = wallList;
	}
	
	//Read in an XML file
	public void fileIn() {
		File f = new File("src/springies/environment.xml");
		if (f.exists()) {
			XMLReader env = new XMLReader("src/springies/environment.xml");

			gravDir = env.readGravity()[0];
			gravMag = env.readGravity()[1] * 0.00005; // Adjusting gravity
			//Should we make a new vector here?
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
	
	public void setWalls(int wall_margin) {
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = springies.displayWidth() - wall_margin * 2
				+ WALL_THICKNESS;
		final double WALL_HEIGHT = springies.displayHeight() - wall_margin * 2
				+ WALL_THICKNESS;

		Wall wall = new Wall(springies, "wall", 2, JGColor.green, WALL_WIDTH,
				WALL_THICKNESS, wallMag[0], wallExp[0]);
		wall.setPos(springies.displayWidth() / 2, wall_margin);
		wallList.add(wall);
		
		wall = new Wall(springies, "wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS, wallMag[2], wallExp[2]);
		wall.setPos(springies.displayWidth() / 2, springies.displayHeight() - wall_margin);
		wallList.add(wall);
		
		wall = new Wall(springies, "wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT, wallMag[3], wallExp[3]);
		wall.setPos(wall_margin, springies.displayHeight() / 2);
		wallList.add(wall);

		wall = new Wall(springies, "wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT, wallMag[1], wallExp[1]);
		wallList.add(wall);
		wall.setPos(springies.displayWidth() - wall_margin,springies.displayHeight() / 2);
	}
	
	public void makeAssembly(ArrayList<HashMap<String, Mass>> massMaps, ArrayList<ArrayList<Spring>> springArrays) {
		FileDialog selector = new FileDialog(new Frame());
		selector.setVisible(true);
		XMLReader reader = new XMLReader("src/springies/" + selector.getFile());
		massMaps.add(reader.makeMasses());
		springArrays.add(reader.makeSprings());
		springArrays.add(reader.makeMuscles());
	}
	
}
