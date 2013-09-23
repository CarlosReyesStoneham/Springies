package springies;

import java.awt.FileDialog;
import java.awt.Frame;

import walls.HorizontalWall;
import walls.VerticalWall;
import walls.Wall;
import xml.objectXMLReader;

public class BoardSetup {

	private static final int WALLZERO = 0;
	private static final int WALLONE = 1;
	private static final int WALLTWO = 2;
	private static final int WALLTHREE = 3;
	private static final int WALLFOUR = 4;
	private static final String SRC = "src/xmlfiles/";

	public Springies mySpringies;
	public Wall[] myWalls = new Wall[WALLFOUR];

	public BoardSetup(Springies springies, double[] wallMags, double[] wallExps) {
		this.mySpringies = springies;
		this.setWalls(wallMags, wallExps);
		makeAssembly();
	}

	public static int wall_margin = 10;

	public void setWalls(double[] wallMag, double wallExp[]) {

		// 0 is the top wall and then it goes clockwise
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = mySpringies.displayWidth() - wall_margin * 2
				+ WALL_THICKNESS;
		final double WALL_HEIGHT = mySpringies.displayHeight() - wall_margin
				* 2 + WALL_THICKNESS;

		myWalls[WALLZERO] = new HorizontalWall(mySpringies, WALL_WIDTH,
				WALL_THICKNESS, wallMag[0], wallExp[0]);
		myWalls[WALLZERO].setPos(mySpringies.displayWidth() / 2, wall_margin);

		myWalls[WALLONE] = new VerticalWall(mySpringies, WALL_THICKNESS,
				WALL_HEIGHT, wallMag[1], wallExp[1]);
		myWalls[WALLONE].setPos(mySpringies.displayWidth() - wall_margin,
				mySpringies.displayHeight() / 2);

		myWalls[WALLTWO] = new HorizontalWall(mySpringies, WALL_WIDTH,
				WALL_THICKNESS, wallMag[2], wallExp[2]);
		myWalls[WALLTWO].setPos(mySpringies.displayWidth() / 2,
				mySpringies.displayHeight() - wall_margin);

		myWalls[WALLTHREE] = new VerticalWall(mySpringies, WALL_THICKNESS,
				WALL_HEIGHT, wallMag[WALLTHREE], wallExp[WALLTHREE]);
		myWalls[WALLTHREE].setPos(wall_margin, mySpringies.displayHeight() / 2);
	}

	public void makeAssembly() {
		FileDialog selector = new FileDialog(new Frame());
		selector.setVisible(true);
		if (selector.getFile() != null
				&& !selector.getFile().equals("environment.xml")) {
			objectXMLReader reader = new objectXMLReader(SRC
					+ selector.getFile());

			mySpringies.addMassMap(reader.makeMasses());
			reader.makeSprings();
			reader.makeMuscles();
		}
	}

	public Wall[] getWalls() {
		return myWalls;
	}
	
	public static void changeMargin(int delta){
		wall_margin += delta;
	}
}
