package jboxGlue;

import org.jbox2d.common.Vec2;
import jgame.JGColor;

public class Spring extends PhysicalObject {
	private static final int DEFAULT_K = 1;
	
	private Mass myFirst;
	private Mass mySecond;
	private double myLength;
	private double myK;

	public Spring(Mass first, Mass second) {
		this(first, second, getLength(first, second), DEFAULT_K);
	}
	
	public Spring(Mass first, Mass second, double length){
		this(first, second, length, DEFAULT_K);
	}
	
	public Spring(Mass first, Mass second, double length, double k) {
		super("Spring", 3, JGColor.white);
		myLength = length;
		myK = k;
		myFirst = first;
		mySecond = second;
		first.addSpring(this);
		second.addSpring(this);
	}

	@Override
	public void paintShape() {
		myEngine.setColor(myColor);
		myEngine.drawLine(myFirst.x, myFirst.y, mySecond.x, mySecond.y);
	}

	public Vec2 getForce(double x, double y) {
		// force is -k*x
		float magnitude = (float) (myK * (myLength - getLength(myFirst,
				mySecond)));
		// either the myFirst or mySecond coordinates should be equivalent and
		// so have no effect
		Vec2 direction = new Vec2((float) ((x - myFirst.x) + (x - mySecond.x)),
				(float) ((y - myFirst.y) + (y - mySecond.x)));
		direction.normalize();
		return direction.mul(magnitude);
	}

	private static double getLength(Mass one, Mass two) {
		return Math.sqrt(Math.pow(one.x - two.x, 2)
				+ Math.pow(one.y - two.x, 2));
	}
	
	protected double getLength(){
		return myLength;
	}
	
	protected void setLength(double newLength){
		myLength = newLength;
	}
}
