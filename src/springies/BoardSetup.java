package springies;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import jboxGlue.Wall;

public class BoardSetup {
	double gravDir;
	double gravMag;
	double viscosity;
	double cmMag;
	double cmExp;
	double[] wallMag = new double[4];
	double[] wallExp = new double[4];

	public Springies springies;

	public BoardSetup(Springies springies) {
		this.springies = springies;
	}

	// Read in an XML file
	public void fileIn() {
		File f = new File("src/springies/environment.xml");
		if (f.exists()) {
			XMLReader env = new XMLReader("src/springies/environment.xml");

			gravDir = env.readGravity()[0];
			gravMag = env.readGravity()[1] * 0.00005; // Adjusting gravity
			// Should we make a new vector here?
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

		Wall wall = new Wall(springies, WALL_WIDTH, WALL_THICKNESS, wallMag[0],
				wallExp[0]);
		wall.setPos(springies.displayWidth() / 2, wall_margin);

		wall = new Wall(springies, WALL_WIDTH, WALL_THICKNESS, wallMag[2],
				wallExp[2]);
		wall.setPos(springies.displayWidth() / 2, springies.displayHeight()
				- wall_margin);

		wall = new Wall(springies, WALL_THICKNESS, WALL_HEIGHT, wallMag[3],
				wallExp[3]);
		wall.setPos(wall_margin, springies.displayHeight() / 2);

		wall = new Wall(springies, WALL_THICKNESS, WALL_HEIGHT, wallMag[1],
				wallExp[1]);
		wall.setPos(springies.displayWidth() - wall_margin,
				springies.displayHeight() / 2);
	}

	public void makeAssembly() {
		FileDialog selector = new FileDialog(new Frame());
		selector.setVisible(true);
		XMLReader reader = new XMLReader("src/springies/" + selector.getFile());

		springies.addMassMap(reader.makeMasses());
		reader.makeSprings();
		reader.makeMuscles();
	}
}
