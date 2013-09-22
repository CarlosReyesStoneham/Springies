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
		setBBox((int)(this.x - this.myWidth/2), (int)(this.y - this.myHeight /2), (int)this.myWidth, (int)this.myHeight);
	}

}
