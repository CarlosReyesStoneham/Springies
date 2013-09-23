package jboxGlue;

import jgame.JGObject;

import org.jbox2d.common.Vec2;

import walls.HorizontalWall;
import walls.VerticalWall;

/**
 * MovableMasses are the objects that are connected by
 * springs in this physics simulation. These masses
 * can be moved. This class could be extended
 * to make many different types of masses that interact
 * differently with the environment or have different shapes
 * when making different kinds of movable masses.
 * Masses depend on Mass class.
 * @author carlosreyes and leevianagray
 *
 */
public class MovableMass extends Mass {
	public MovableMass(float x, float y, float mass) {
		this(x, y, 0, 0, mass);
	}
	
	/** Default Constructor. */
	public MovableMass(float x, float y, double xForce, double yForce,
			float mass) {
		super(x, y, mass);
		setX(x);
		myBody.m_type = 1;
		this.setForce(xForce, yForce);
	}
	
	/**
	 * Applies a force to the MovableMass object.
	 * This force is read in as a Vec2 parameter
	 * to determine the strength and direction of the
	 * force being applied.
	 */
	@Override
	public void applyForce(Vec2 force) {
		myBody.applyForce(force, myBody.getLocalCenter());
	}
	
	/**
	 * Applies multiple to the MovableMass object.
	 * This force is read in as two Vec2 parameters
	 * to determine the strength and direction of the
	 * forces being applied. Returns void.
	 */
	public void applyForce(Vec2 force, Vec2 force2) {
		myBody.applyForce(force, force2);
	}
	
	/**
	 * Moves the MovableMass.
	 * Returns void.
	 */
	@Override
	public void move() {
		//super.move();

		Vec2 position = myBody.getPosition();
		y = position.y;
		x = position.x;
		setX(position.x);
		myRotation = -myBody.getAngle();
		// Applying the spring force
		for (Spring s : mySprings) {
			Vec2 force = s.getForce(x, y);
			this.setForce(force.x, force.y);
		}
	}
	
	/**
	 * Determines where the masses move when hit by either a
	 * top or bottom wall. This is used when resizing the walls
	 * to move the masses inward as the walls move inward. It is 
	 * also used to determine how the masses will bounce if they hit
	 * the walls.
	 * Returns void.
	 */
	// @Override
	public void hit(JGObject other) {
		// if hits top or bottom
		if (other instanceof HorizontalWall) {
			if(myBody.m_linearVelocity.length() <= 1) {
				if(other.x < pfheight/2){
					myBody.m_linearVelocity = new Vec2(0, -2f);
				}
				else
					myBody.m_linearVelocity = new Vec2(0, 2f);
			}
			myBody.m_linearVelocity = (new Vec2(myBody.m_linearVelocity.x,
					-myBody.m_linearVelocity.y));
		}
		// if hits left or right
		if (other instanceof VerticalWall) {
			if(myBody.m_linearVelocity.length() <= 1) {
				if(other.x < pfwidth/2){
					myBody.m_linearVelocity = new Vec2(-2f,0);
				}
				else
					myBody.m_linearVelocity = new Vec2(2f,0);
			}
			myBody.m_linearVelocity = (new Vec2(-myBody.m_linearVelocity.x,
					myBody.m_linearVelocity.y));
		}
	}
}
