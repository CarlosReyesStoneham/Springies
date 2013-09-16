package jboxGlue;

import org.jbox2d.common.Vec2;

import jgame.JGObject;

public class MovableMass extends Mass {
	private final static int MARGIN = 25;
	
	public MovableMass(float x, float y, float mass) {
		this(x, y, 0, 0, mass);
	}
	
	public MovableMass(float x, float y, double xForce, double yForce, float mass) {
		super(x, y, mass);
		myBody.m_type = 1;
		this.setForce(xForce, yForce);
	}
	
	@Override
	public void applyForce(Vec2 force){
		myBody.applyForce(force, myBody.getLocalCenter());
	}
	public void applyForce(Vec2 force, Vec2 force2){
		myBody.applyForce(force, force2);
	}

	@Override
	public void move() {
		super.move();
		
		Vec2 position = myBody.getPosition();
		y = position.y;
		x = position.x;
		myRotation = -myBody.getAngle();
		
		//Applying the spring force
		for (Spring s : mySprings) {
			Vec2 force = s.getForce(x, y);
			this.setForce(force.x, force.y);
		}
	}	
	
	@Override
	public void hit(JGObject other) {		
		//if hits top or bottom
		if (and(other.colid, 2) && (myBody.getPosition().y < MARGIN || myBody.getPosition().y > pfheight-MARGIN)) {
			myBody.m_linearVelocity = (new Vec2(myBody.m_linearVelocity.x, -myBody.m_linearVelocity.y));
		}
		//if hits left or right
		if (and(other.colid, 2) && (myBody.getPosition().x < MARGIN || myBody.getPosition().x > pfwidth-MARGIN)) {
			myBody.m_linearVelocity = (new Vec2(-myBody.m_linearVelocity.x, myBody.m_linearVelocity.y));
		}

	}
}
