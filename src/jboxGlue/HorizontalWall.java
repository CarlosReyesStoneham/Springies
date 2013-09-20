package jboxGlue;

import springies.Springies;

public class HorizontalWall extends Wall {

	public HorizontalWall(Springies s, double width, double height,
			double wallMag, double wallExp) {
		super(s, width, height, wallMag, wallExp);
	}

	@Override
	public void setThickness(int delta) {
		myHeight += delta;
	//	initWall();
	}
}
