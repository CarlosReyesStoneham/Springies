package jboxGlue;

import java.util.ArrayList;
import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;

import jgame.JGColor;

/**
 * Masses are the objects that are connected by
 * springs in this physics simulation. These masses
 * are immobile and will simply sit still when generated
 * on the playing field. This class could be extended
 * to make many diffent types of masses that interact
 * differently with the environment or have different shapes.
 * Masses depend on PhysicalObjects class.
 * @author carlosreyes and leevianagray
 *
 */
public class Mass extends PhysicalObject {
	protected static final int RADIUS = 10;
	protected ArrayList<Spring> mySprings = new ArrayList<Spring>();
	private static final String MASS = "Mass";
	private float mass;
	private float myX;

	/** Contructor. */
	public Mass(float x, float y, float mass) {
		super(MASS, 1, JGColor.blue);
		init(mass);
		this.setPos(pfwidth - 20 - x, pfheight - 20 - y);
		myBody.m_type = 0;
		this.mass = mass;
	}
	
	/**
	 * addSpring adds a Spring object to an
	 * arraylist of springs. Returns void.
	 */
	public void addSpring(Spring newSpring) {
		mySprings.add(newSpring);
	}

	/** 
	 * Default getter method. Returns float type, 
	 * numerical value of the mass.
	 * */
	public float getMyMass() {
		return this.mass;
	}
	
	/**
	 * Inherited from PhysicalObject
	 */
	public void applyForce(Vec2 force) {}
	
	/**
	 * Initialize the mass's physical properties.
	 * Returns void.
	 */
	protected void init(double mass) {
		CircleDef shape = new CircleDef();
		shape.radius = (float) RADIUS;
		shape.density = (float) mass;
		createBody(shape);
		setBBox(-RADIUS, -RADIUS, 2 * RADIUS, 2 * RADIUS);
	}
	
	/**
	 * Creates a visual object on the playing field. Returns void.
	 */
	@Override
	public void paintShape() {
		myEngine.setColor(myColor);
		myEngine.drawOval(x, y, (float) RADIUS * 2, (float) RADIUS * 2, true,
				true);
	}

	/**
	 * Default getter for myX. Returns float, x position of mass.
	 */
	public float getX() {
		return myX;
	}

	/**
	 * Default setter for myX. Returns void.
	 */
	public void setX(float x) {
		myX = x;
	}
}
