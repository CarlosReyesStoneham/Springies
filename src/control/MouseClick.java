package control;

import java.util.HashMap;
import java.util.ResourceBundle.Control;

import jboxGlue.Mass;
import jboxGlue.PointMass;
import jboxGlue.Spring;

import springies.Springies;

/**
 * MouseClick enables a user to click anywhere on the playingfield
 * and have a spring made between the mouse pointer and the closest
 * mass object. It could be modified to change the action of the mouse
 * such as having multiple items attached by a spring upon a click.
 * Depends on the class Control.
 * @author carlosreyes and leevianagray
 */
public class MouseClick extends Control {
	protected Springies mySpringies;
	protected PointMass myMouseMass;
	private Spring myMouseSpring;

	private static final int LARGENUM = 9999;

	/** Constructor for MouseClick*/
	public MouseClick(Springies springies, PointMass myMouseMass,
			Spring myMouseSpring) {
		this.mySpringies = springies;
		this.myMouseMass = myMouseMass;
		this.myMouseSpring = myMouseSpring;
	}

	/**
	 * Click is called in the checkUserInput method of control,
	 * when the mouse is clicked, a spring is formed between the
	 * mouse and the nearest mass object. When the mouse is released
	 * the spring is removed. Returns void.
	 */
	public void click() {
		if (mySpringies.getMouseButton(1)) {
			if (myMouseMass == null) {
				double shortestPath = LARGENUM;
				Mass closestMass = null;
				int x = mySpringies.getMouseX();
				int y = mySpringies.getMouseY();
				myMouseMass = new PointMass(mySpringies, mySpringies.pfWidth()
						- x, mySpringies.pfHeight() - y, 1);
				myMouseMass.x = x;
				myMouseMass.y = y;

				for (HashMap<String, Mass> massList : mySpringies.getMassMaps()) {
					for (Mass mass : massList.values()) {
						double len = calculateLength(mass, myMouseMass);
						if (len < shortestPath) {
							shortestPath = len;
							closestMass = mass;
						}
					}
				}

				myMouseSpring = new Spring(myMouseMass, closestMass);
			}
		} else {
			if (myMouseMass != null) {
				myMouseMass.remove();
				myMouseSpring.remove();
				myMouseMass = null;
			}
		}
	}
	/**
	 * Calculate length calculates the length between two
	 * masses by using the x and y coordinates of each mass
	 * and finding the hypotenuse of the triangle formed.
	 * This thus calculates the length between two masses.
	 * Returns a double, which is the shortest distance between
	 * the masses.
	 */
	protected double calculateLength(Mass one, Mass two) {
		return Math.sqrt(Math.pow(one.x - two.x, 2)
				+ Math.pow(one.y - two.y, 2));
	}
}
