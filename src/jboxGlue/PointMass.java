package jboxGlue;

import jgame.JGColor;

import springies.Springies;

/**
 * PointMass is a class designed to enable the point of 
 * a mouse object to be connected by a spring to another
 * mass.Depends on MovableMass class.
 * @author carlosreyes and leevianagray.
 */
public class PointMass extends MovableMass {
	Springies mySpringies;
	
	/** Default Constructor. */
	public PointMass(Springies s, float x, float y, float mass) {
		super(x, y, mass);
		mySpringies = s;
	}
	/**
	 * Moves along with the mouse's x and y coordinates.
	 * Returns void.
	 */
	@Override
	public void move() {
		x = mySpringies.getMouseX();
		y = mySpringies.getMouseY();
	}
	/**
	 * Sets the object to be black so that it blends in with the
	 * background. This will break if the background color changes.
	 * Ideally it would be set to whatever the background color is.
	 * Returns void.
	 */
	@Override
	public void paintShape() {
		myEngine.setColor(JGColor.black);
	}
}
