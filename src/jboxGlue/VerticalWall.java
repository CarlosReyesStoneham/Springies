package jboxGlue;

import springies.Springies;

public class VerticalWall extends Wall {

	public VerticalWall(Springies s, double width, double height,
			double wallMag, double wallExp) {
		super(s, width, height, wallMag, wallExp);
	}

	@Override
	public void setThickness(int delta) {
		myWidth += delta;
		// TODO: Object doesn't collide with new wall, figure out why (and then apply this to HorizontalWall too)
		clearBBox();
		setBBox(-(int) myWidth / 2, -(int) myHeight / 2, (int) myWidth, (int) myHeight);
	}

}
