package jboxGlue;

import java.util.ArrayList;

import org.jbox2d.collision.CircleDef;
import org.jbox2d.common.Vec2;

import jgame.JGColor;
import jgame.JGObject;

public class Mass extends PhysicalObject {
	private static final int DEFAULT_MASS = 5;
	
	int myRadius = 10;
	int myMass;
	ArrayList<Spring> mySprings = new ArrayList<Spring>();
	
	public Mass(int x, int y, int xForce, int yForce) {
		this( x, y, xForce, yForce, DEFAULT_MASS);
	}
	
	public Mass(int x, int y, int xForce, int yForce, int mass) {
		super("Mass", 1, JGColor.blue);
		init(mass);
		this.setPos( x, y );
		this.setForce( xForce, yForce );
	}

	public void addSpring(Spring newSpring){
		mySprings.add(newSpring);
	}
	
	private void init(double mass)
	{
		double radius = 10;
		
		// make it a circle
		CircleDef shape = new CircleDef();
		shape.radius = (float)radius;
		shape.density = (float)mass;
		createBody( shape );
		setBBox( -(int)radius, -(int)radius, 2*(int)radius, 2*(int)radius );
	}
	
	@Override
	public void paintShape( )
	{
		myEngine.setColor( myColor );
		myEngine.drawOval( x, y, (float)myRadius*2, (float)myRadius*2, true, true );
	}
	
	@Override
	public void move(){
		Vec2 position = myBody.getPosition();
		x = position.x;
		y = position.y;
		myRotation = -myBody.getAngle();
		
		for(Spring s: mySprings){
			Vec2 force = s.getForce(x, y);	
			myBody.applyForce(force, myBody.getLocalCenter());
		}
	}
	
	@Override
	public void hit( JGObject other )
	{
		// we hit something! bounce off it!
		Vec2 velocity = myBody.getLinearVelocity();
		
		// is it a tall wall?
		final double DAMPING_FACTOR = 0.8;
		boolean isSide = other.getBBox().height > other.getBBox().width;
		if( isSide )
		{
			velocity.x *= -DAMPING_FACTOR;
		}
		else
		{
			velocity.y *= -DAMPING_FACTOR;
		}
		
		// apply the change
		myBody.setLinearVelocity( velocity );
	}
}
