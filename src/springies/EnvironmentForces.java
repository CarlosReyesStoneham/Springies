package springies;

import java.io.File;
import java.util.HashMap;

import org.jbox2d.common.Vec2;

import jboxGlue.Mass;
import jboxGlue.WorldManager;

public class EnvironmentForces {
	double myGravDir;
	double myGravMag;
	double myViscosity;
	double myCMMag;
	double myCMExp;
	double[] myWallMag = new double[4];
	double[] myWallExp = new double[4];
	HashMap<String, Boolean> myToggles = new HashMap<String, Boolean>();

	public Springies springies;

	public EnvironmentForces(Springies springies) {
		this.springies = springies;
		readForces();
		myToggles.put("Gravity", true);
		myToggles.put("Viscosity", true);
		myToggles.put("COM", true);
	}

	private void readForces() {
		// Read in an XML file
		File f = new File("src/springies/environment.xml");
		if (f.exists()) {
			XMLReader env = new XMLReader("src/springies/environment.xml");

			myGravDir = env.readGravity()[0];
			myGravMag = env.readGravity()[1] * 0.00005; // Adjusting gravity
			// Should we make a new vector here?
			myViscosity = env.readViscosity();
			myCMMag = env.readcm()[0];
			myCMExp = env.readcm()[1];
			myWallMag = env.readWallMag();
			myWallExp = env.readWallExp();
		} else {
			myGravDir = 0;
			myGravMag = 0;
			myViscosity = 1;
			myCMMag = 0;
			myCMExp = 0;
			myWallMag = new double[4];
			myWallExp = new double[4];
		}
	}

	public void doForces() {
		gravForce();
		viscosityForce();
		// COMForce();
	}

	public void toggle(String force) {
		myToggles.put(force, myToggles.get(force) ^ true);
	}

	public void gravForce() {
		if (myToggles.get("Gravity")) {
			// We need to take into account the case where gravDir > 90 degrees
			// This does take that into account...?
			WorldManager.getWorld().setGravity(
					new Vec2((float) (myGravMag * Math.cos(Math
							.toRadians(myGravDir))), (float) (myGravMag * Math
							.cos(Math.toRadians(myGravDir)))));
		} else {
			WorldManager.getWorld().setGravity(new Vec2(0f, 0f));
		}
	}

	private void viscosityForce() {
		if (myToggles.get("Viscosity")) {
			for (HashMap<String, Mass> massList : springies.getMassMaps()) {
				for (Mass m : massList.values()) {
					m.xspeed = m.xspeed * myViscosity;
					m.yspeed = m.yspeed * myViscosity;
				}
			}
		}
	}

	public void COMForce() {
		if (myToggles.get("COM")) {
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

	public double[] getWallMags() {
		return myWallMag;
	}

	public double[] getWallExps() {
		return myWallExp;
	}
}