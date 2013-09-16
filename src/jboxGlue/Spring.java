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
	
	public Spring(Mass first, Mass second, double k){
		this(first, second, getLength(first, second), k);
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
	
//	public void addSpringForce() {
//        Vec2 pA = myFirst.myBody.getPosition();
//        Vec2 pB = mySecond.myBody.getPosition();
//        Vec2 diff = pB.sub(pA);
//        Vec2 vA = myFirst.myBody.m_linearVelocity;
//        Vec2 vB = mySecond.myBody.m_linearVelocity;
//        Vec2 vdiff = vB.sub(vA);
//        float dx = diff.normalize(); //normalizes diff and puts length into dx
//        float vrel = vdiff.x*diff.x + vdiff.y*diff.y;
//        float forceMag = (float) (-DEFAULT_K*(dx-myLength) - 0.5*vrel);
//        diff.mulLocal(forceMag); // diff *= forceMag
//        mySecond.myBody.applyForce(diff, myFirst.myBody.getPosition());
//        myFirst.myBody.applyForce(diff.mulLocal(-1f), mySecond.myBody.getPosition());
//        myFirst.myBody.wakeUp();
//        mySecond.myBody.wakeUp();        
//    }
	
	public Vec2 getForce(double x, double y) {
		// force is -k*x
		if(this.getLength() > 60) {
			
		}
		float magnitude = (float) (myK * (myLength - getLength(myFirst,
				mySecond)));
		// either the myFirst or mySecond coordinates should be equivalent and
		// so have no effect
		Vec2 direction = new Vec2((float) ((x - myFirst.x) + (x - mySecond.x)),
				(float) ((y - myFirst.y) + (y - mySecond.y)));
		
		direction.normalize();
		direction = direction.mul(magnitude);
		return direction;
	}

	private static double getLength(Mass one, Mass two) {
		return Math.sqrt(Math.pow(one.x - two.x, 2)
				+ Math.pow(one.y - two.y, 2));
	}
	
	protected double getLength(){
		return myLength;
	}
	
	protected void setLength(double newLength){
		myLength = newLength;
	}
}
