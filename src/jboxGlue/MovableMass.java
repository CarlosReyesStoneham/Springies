package jboxGlue;

import org.jbox2d.common.Vec2;
import jgame.JGObject;

public class MovableMass extends Mass {
	
	private static final int DEFAULT_MASS = 5;
	// default velocity 0?
	public MovableMass(float x, float y) {
		this(x, y, 0, 0, DEFAULT_MASS);
	}
	
	public MovableMass(float x, float y, double xForce, double yForce, int mass) {
		super(x, y, mass);
		myBody.m_type = 1;
		this.setForce(xForce, yForce);
	}
	
	public void applyForce(Vec2 force){
		myBody.applyForce(force, myBody.getLocalCenter());
	}

	@Override
	public void move() {
		Vec2 position = myBody.getPosition();
		y = position.y;
		x = position.x;
		//System.out.println("X: " + x + "Y: " + y);
		myRotation = -myBody.getAngle();
		
		if(x > pfwidth-20)
			x = pfwidth-40;
		else if(x < 20)
			x = 40;
		
		if(y > pfheight-20)
			y = pfheight-40;
		else if(y < 20)
			y = 40;
		
		//System.out.println("New | X: " + x + " Y: " + y);
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
