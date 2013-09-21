package springies;

import java.awt.FileDialog;
import java.awt.Frame;

import jboxGlue.HorizontalWall;
import jboxGlue.VerticalWall;
import jboxGlue.Wall;

public class BoardSetup {
	public Springies mySpringies;
	public Wall[] myWalls = new Wall[4];

	public BoardSetup(Springies springies, double[] wallMags, double[] wallExps) {
		this.mySpringies = springies;
		this.setWalls(wallMags, wallExps);
		makeAssembly();
	}

	public void setWalls(double[] wallMag, double wallExp[]) {
		int wall_margin = 10;
		// 0 is the top wall and then it goes clockwise
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = mySpringies.displayWidth() - wall_margin * 2
				+ WALL_THICKNESS;
		final double WALL_HEIGHT = mySpringies.displayHeight() - wall_margin
				* 2 + WALL_THICKNESS;

		myWalls[0] = new HorizontalWall(mySpringies, WALL_WIDTH,
				WALL_THICKNESS, wallMag[0], wallExp[0]);
		myWalls[0].setPos(mySpringies.displayWidth() / 2, wall_margin);

		myWalls[1] = new VerticalWall(mySpringies, WALL_THICKNESS, WALL_HEIGHT,
				wallMag[1], wallExp[1]);
		myWalls[1].setPos(mySpringies.displayWidth() - wall_margin,
				mySpringies.displayHeight() / 2);

		myWalls[2] = new HorizontalWall(mySpringies, WALL_WIDTH,
				WALL_THICKNESS, wallMag[2], wallExp[2]);
		myWalls[2].setPos(mySpringies.displayWidth() / 2,
				mySpringies.displayHeight() - wall_margin);

		myWalls[3] = new VerticalWall(mySpringies, WALL_THICKNESS, WALL_HEIGHT,
				wallMag[3], wallExp[3]);
		myWalls[3].setPos(wall_margin, mySpringies.displayHeight() / 2);
	}

	public void makeAssembly() {
		FileDialog selector = new FileDialog(new Frame());
		selector.setVisible(true);
		if (selector.getFile() != null) {
			XMLReader reader = new XMLReader("src/springies/"
					+ selector.getFile());

			mySpringies.addMassMap(reader.makeMasses());
			reader.makeSprings();
			reader.makeMuscles();
		}
	}

	public Wall[] getWalls() {
		return myWalls;
	}
}
