package springies;

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
	HashMap<String, Boolean> toggles = new HashMap<String, Boolean>();

	public Springies springies;

	public EnvironmentForces(Springies springies) {
		this.springies = springies;
		toggles.put("Gravity", true);
		toggles.put("Viscosity", true);
		toggles.put("COM", true);
	}

	public void doForces() {
		gravForce();
		viscosityForce();
		// COMForce();	
	}

	public void toggle(String force){
		toggles.put(force, toggles.get(force) ^ true);
	}
	
	public void gravForce() {
		if (toggles.get("Gravity")) {
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
	
	private void viscosityForce() {
		if(toggles.get("Viscosity")){
			for (HashMap<String, Mass> massList : springies.getMassMaps()) {
				for (Mass m : massList.values()) {
					m.xspeed = m.xspeed * viscosity;
					m.yspeed = m.yspeed * viscosity;
				}
			}
		}
	}
	
	public void COMForce() {
		if(toggles.get("COM")){
			float topX = 0;
			float topY = 0;
			float totalMass = 500;
			// TODO: where are you using cmMag and cmExp?
			for (HashMap<String, Mass> massList : springies.getMassMaps()) {
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
}