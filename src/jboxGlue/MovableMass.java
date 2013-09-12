package jboxGlue;

import org.jbox2d.common.Vec2;
import jgame.JGObject;

public class MovableMass extends Mass {
	
	private static final int DEFAULT_MASS = 5;
	// default velocity 0?
	public MovableMass(int x, int y) {
		this(x, y, 0, 0, DEFAULT_MASS);
	}
	
	public MovableMass(int x, int y, int xForce, int yForce, int mass) {
		super(x, y, mass);
		myBody.m_type = 1;
		this.setForce(xForce, yForce);
	}

	@Override
	public void move() {
		Vec2 position = myBody.getPosition();
		x = position.x;
		y = position.y;
		myRotation = -myBody.getAngle();

		for (Spring s : mySprings) {
			Vec2 force = s.getForce(x, y);
			myBody.applyForce(force, myBody.getLocalCenter());
		}
	}

	@Override
	public void hit(JGObject other) {
		// we hit something! bounce off it!
		Vec2 velocity = myBody.getLinearVelocity();

		// is it a tall wall?
		final double DAMPING_FACTOR = 0.8;
		boolean isSide = other.getBBox().height > other.getBBox().width;
		if (isSide) {
			velocity.x *= -DAMPING_FACTOR;
		} else {
			velocity.y *= -DAMPING_FACTOR;
		}

		// apply the change
		myBody.setLinearVelocity(velocity);
	}
}
