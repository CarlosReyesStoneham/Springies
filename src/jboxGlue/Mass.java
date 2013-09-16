package jboxGlue;

import java.util.ArrayList;
import org.jbox2d.collision.CircleDef;
import jgame.JGColor;

public class Mass extends PhysicalObject {
	private static final int RADIUS = 10;

	protected ArrayList<Spring> mySprings = new ArrayList<Spring>();
	
	public Mass(float x, float y, int mass) {
		super("Mass", 1, JGColor.blue);
		init(mass);
		this.setPos(x, y);
		System.out.println("Set position to:" + x + "," + y);
		myBody.m_type = 0;
	}

	public void addSpring(Spring newSpring) {
		mySprings.add(newSpring);
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
}
