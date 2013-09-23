package jboxGlue;

import jgame.JGColor;

import springies.Springies;

public class PointMass extends MovableMass {
	Springies mySpringies;

	public PointMass(Springies s, float x, float y, float mass) {
		super(x, y, mass);
		mySpringies = s;
	}

	@Override
	public void move() {
		x = mySpringies.getMouseX();
		y = mySpringies.getMouseY();
	}

	@Override
	public void paintShape() {
		myEngine.setColor(JGColor.black);
	}
}
