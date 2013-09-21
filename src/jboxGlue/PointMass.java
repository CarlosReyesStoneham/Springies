package jboxGlue;

import jgame.JGColor;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;

public class PointMass extends Mass{

	public PointMass(float x, float y, float mass) {
		super(x, y, mass);
		// TODO Auto-generated constructor stub
	}
	
	public void addSpring(Spring newSpring) {
		mySprings.add(newSpring);
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
	protected void init(double mass) {
		CircleDef shape = new CircleDef();
		shape.radius = (float) RADIUS;
		shape.density = (float) mass;
		createBody(shape);
		setBBox(-RADIUS, -RADIUS, 2 * RADIUS, 2 * RADIUS);
	}

	@Override
	public void paintShape() {
		myEngine.setColor(JGColor.black);
		myEngine.drawOval(x, y, (float) RADIUS * 2, (float) RADIUS * 2, true,
				true);
	}
}
