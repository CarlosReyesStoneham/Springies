package jboxGlue;

import java.util.ArrayList;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;

import jgame.JGColor;

public class Mass extends PhysicalObject {
	protected static final int RADIUS = 10;
	protected ArrayList<Spring> mySprings = new ArrayList<Spring>();
	private static final String MASS = "Mass";
	private float mass;
	private float myX;

	public Mass(float x, float y, float mass) {
		super(MASS, 1, JGColor.blue);
		init(mass);
		this.setPos(pfwidth - 20 - x, pfheight - 20 - y);
		myBody.m_type = 0;
		this.mass = mass;
	}

	public void addSpring(Spring newSpring) {
		mySprings.add(newSpring);
	}

	public float getMyMass() {
		return this.mass;
	}

	public void applyForce(Vec2 force) {
	}

	protected void init(double mass) {
		CircleDef shape = new CircleDef();
		shape.radius = (float) RADIUS;
		shape.density = (float) mass;
		createBody(shape);
		setBBox(-RADIUS, -RADIUS, 2 * RADIUS, 2 * RADIUS);
	}

	@Override
	public void paintShape() {
		myEngine.setColor(myColor);
		myEngine.drawOval(x, y, (float) RADIUS * 2, (float) RADIUS * 2, true,
				true);
	}

	// workaround for the strangest bug ever.
	public float getX() {
		return myX;
	}

	public void setX(float x) {
		myX = x;
	}
}
