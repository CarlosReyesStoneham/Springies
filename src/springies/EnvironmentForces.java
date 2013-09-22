package springies;

import java.io.File;
import java.util.HashMap;

import org.jbox2d.common.Vec2;

import xml.XMLReader;

import jboxGlue.Mass;
import jboxGlue.WorldManager;

public class EnvironmentForces {
	private static final double GRAVADJ = 0.00005;
	private static final int TOTALMASS = 500;
	private static final String COM = "COM";
	private static final String ENV = "src/springies/environment.xml";
	private static final String GRAV = "Gravity";
	private static final String VISC = "Viscosity";
	
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
		myToggles.put(GRAV, true);
		myToggles.put(VISC, true);
		myToggles.put(COM, true);
	}

	private void readForces() {
		// Read in an XML file
		File f = new File(ENV);
		if (f.exists()) {
			XMLReader env = new XMLReader(ENV);

			myGravDir = env.readGravity()[0];
			myGravMag = env.readGravity()[1] * GRAVADJ; // Adjusting gravity
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
		if (myToggles.get(GRAV)) {
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
		if (myToggles.get(VISC)) {
			for (HashMap<String, Mass> massList : springies.getMassMaps()) {
				for (Mass m : massList.values()) {
					m.xspeed = m.xspeed * myViscosity;
					m.yspeed = m.yspeed * myViscosity;
				}
			}
		}
	}

	public void COMForce() {
		if (myToggles.get(COM)) {
			float topX = 0;
			float topY = 0;
			float totalMass = TOTALMASS;
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