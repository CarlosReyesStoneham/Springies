package jboxGlue;

import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import jgame.JGColor;

/**
 * Spring class makes an elastic line between two
 * masses. Its spring motion is determinded by Hooke's law.
 * The Spring class could be extended to make the spring have
 * non-normal properties.
 * Spring depends on Physical object.
 * @author carlosreyes and leevianagray
 *
 */
public class Spring extends PhysicalObject {
	private static final int DEFAULT_K = 1;
	private static final double kAdjust = 1;
	private Mass myFirst;
	private Mass mySecond;
	private double myLength;
	private double myK;
	
	/** Constructor. */
	public Spring(Mass first, Mass second) {
		this(first, second, calculateLength(first, second), DEFAULT_K);
	}
	/** Constructor. */
	public Spring(Mass first, Mass second, double k) {
		this(first, second, calculateLength(first, second), k);
	}
	/** Constructor. */
	public Spring(Mass first, Mass second, double length, double k) {
		super("Spring", 3, JGColor.white);
		myLength = length;
		myK = k * kAdjust;
		myFirst = first;
		mySecond = second;
		first.addSpring(this);
		second.addSpring(this);

		this.createBody(new PolygonDef());
	}
	
	/** Makes an image of the line on the playing field. Returns void. */
	@Override
	public void paintShape() {
		myEngine.setColor(myColor);
		myEngine.drawLine(myFirst.x, myFirst.y, mySecond.x, mySecond.y);
	}

	/**
	 * The spring force and motion is being set.
	 * Returns Vec2 type, giving a direction for force to
	 * be applied.
	 */
	public Vec2 getForce(double x, double y) {
		// force is -k*x
		if (this.getLength() > 60) {
		}

		float magnitude = (float) (myK * (myLength - calculateLength(myFirst,
				mySecond)));
		// either the myFirst or mySecond coordinates should be equivalent and
		// so have no effect
		Vec2 direction = new Vec2((float) ((x - myFirst.x) + (x - mySecond.x)),
				(float) ((y - myFirst.y) + (y - mySecond.y)));

		direction.normalize();
		direction = direction.mul(magnitude);
		return direction;
	}
	/**
	 * Calculate length calculates the length between two
	 * masses by using the x and y coordinates of each mass
	 * and finding the hypotenuse of the triangle formed.
	 * This thus calculates the length between two masses.
	 * Returns a double, which is the shortest distance between
	 * the masses.
	 */
	private static double calculateLength(Mass one, Mass two) {
		return Math.sqrt(Math.pow(one.x - two.x, 2)
				+ Math.pow(one.y - two.y, 2));
	}
	
	/**
	 * Default length getter. Returns double.
	 */
	protected double getLength() {
		return myLength;
	}

	/**
	 * Default length setter. Returns void.
	 */
	protected void setLength(double newLength) {
		myLength = newLength;
	}
}
