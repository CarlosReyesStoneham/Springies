package springies;

import java.util.ArrayList;
import java.util.HashMap;

import org.jbox2d.common.Vec2;

import jboxGlue.Mass;
import jboxGlue.WorldManager;

public class EnvironmentForces {
	double gravDir;
	double gravMag;
	double viscosity;
	double cmMag;
	double cmExp;
	double[] wallMag = new double[4];
	double[] wallExp = new double[4];

	public Springies springies;

	public EnvironmentForces(Springies springies) {
		this.springies = springies;
	}

	public void calculateGravitationalForce(int toggleGravity) {
		if (toggleGravity == 0) {
			// We need to take into account the case where gravDir > 90 degrees
			// This does take that into account...?
			WorldManager.getWorld().setGravity(
					new Vec2((float) (gravMag * Math.cos(Math
							.toRadians(gravDir))), (float) (gravMag * Math
							.cos(Math.toRadians(gravDir)))));
		} else {
			WorldManager.getWorld().setGravity(new Vec2(0f, 0f));
		}
	}

	public void centerOfMass(ArrayList<HashMap<String, Mass>> massMaps) {
		float topX = 0;
		float topY = 0;
		float totalMass = 500;
		// TODO: where are you using cmMag and cmExp?
		for (HashMap<String, Mass> massList : massMaps) {
			for (Mass m : massList.values()) {
				topX += (float) (m.getMyMass() * m.x);
				topY += (float) (m.getMyMass() * m.y);
			}
		}

		float xCoord = (float) (topX / totalMass);
		float yCoord = (float) (topY / totalMass);
		new Mass(xCoord, yCoord, 1);
	}
}