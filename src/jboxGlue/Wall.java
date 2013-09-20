package jboxGlue;

import java.util.HashMap;

import jgame.JGColor;

import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;

import springies.Springies;

public class Wall extends PhysicalObject {
	private double myWidth;
	private double myHeight;
	private double[] myPolyx;
	private double[] myPolyy;
	private Springies mySpringies;
	private double myWallMag;
	private double myWallExp;

	public Wall(Springies s, String id, int collisionId, JGColor color,
			double width, double height, double wallMag, double wallExp) {
		this(s, id, collisionId, color, width, height, wallMag, wallExp, 0);
	}

	public Wall(Springies s, String id, int collisionId, JGColor color,
			double width, double height, double wallMag, double wallExp, double mass) {
		super(id, collisionId, color);
		init(width, height, mass);
		mySpringies = s;
		myWallMag = wallMag;
		myWallExp = wallExp;
	}

	public void init(double width, double height, double mass) {
		// save arguments
		myWidth = width;
		myHeight = height;

		// init defaults
		myPolyx = null;
		myPolyy = null;

		// make it a rect
		PolygonDef shape = new PolygonDef();
		shape.density = (float) mass;
		shape.setAsBox((float) width, (float) height);
		createBody(shape);
		setBBox(-(int) width / 2, -(int) height / 2, (int) width, (int) height);
	}

	@Override
	public void paintShape() {
		if (myPolyx == null || myPolyy == null) {
			// allocate memory for the polygon
			myPolyx = new double[4];
			myPolyy = new double[4];
		}

		// draw a rotated polygon
		myEngine.setColor(myColor);
		double cos = Math.cos(myRotation);
		double sin = Math.sin(myRotation);
		double halfWidth = myWidth / 2;
		double halfHeight = myHeight / 2;
		myPolyx[0] = (int) (x - halfWidth * cos - halfHeight * sin);
		myPolyy[0] = (int) (y + halfWidth * sin - halfHeight * cos);
		myPolyx[1] = (int) (x + halfWidth * cos - halfHeight * sin);
		myPolyy[1] = (int) (y - halfWidth * sin - halfHeight * cos);
		myPolyx[2] = (int) (x + halfWidth * cos + halfHeight * sin);
		myPolyy[2] = (int) (y - halfWidth * sin + halfHeight * cos);
		myPolyx[3] = (int) (x - halfWidth * cos + halfHeight * sin);
		myPolyy[3] = (int) (y + halfWidth * sin + halfHeight * cos);
		myEngine.drawPolygon(myPolyx, myPolyy, null, 4, true, true);
	}
	
	@Override
	public void move() {
		// copy the position and rotation from the JBox world to the JGame world
		Vec2 position = myBody.getPosition();
		x = position.x;
		y = position.y;
		myRotation = -myBody.getAngle();

		for(HashMap<String, Mass> massMap: mySpringies.getMassMaps()){
			for(Mass m: massMap.values()){
				m.applyForce(new Vec2( (float) (myWallMag / Math.pow(m.x,
						myWallExp)), 0)); 
			}
		}
	}
}