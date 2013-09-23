package control;

import java.util.HashMap;
import java.util.ResourceBundle.Control;

import jboxGlue.Mass;
import jboxGlue.PointMass;
import jboxGlue.Spring;

import springies.Springies;

public class MouseClick extends Control {
	protected Springies mySpringies;
	protected PointMass myMouseMass;
	private Spring myMouseSpring;

	private static final int LARGENUM = 9999;

	public MouseClick(Springies springies, PointMass myMouseMass,
			Spring myMouseSpring) {
		this.mySpringies = springies;
		this.myMouseMass = myMouseMass;
		this.myMouseSpring = myMouseSpring;
	}

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

	protected double calculateLength(Mass one, Mass two) {
		return Math.sqrt(Math.pow(one.x - two.x, 2)
				+ Math.pow(one.y - two.y, 2));
	}
}
