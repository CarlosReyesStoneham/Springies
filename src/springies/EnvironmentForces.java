package springies;

import java.io.File;
import java.util.HashMap;

import org.jbox2d.common.Vec2;
import xml.envXMLReader;
import jboxGlue.Mass;
import jboxGlue.WorldManager;
/**
 * Sets the environment forces based on 
 * either what was read from the XML file or sets
 * default forces if no environment xmlfile exists.
 * @author carlosreyes and leevianagray
 *
 */
public class EnvironmentForces {
	private static final double GRAVADJ = 0.0000005;
	private static final String COM = "COM";
	private static final String ENV = "src/xmlfiles/environment.xml";
	private static final String GRAV = "Gravity";
	private static final String VISC = "Viscosity";

	// Environment variables
	double myGravDir;
	double myGravMag;
	double myViscosity;
	double myCMMag;
	double myCMExp;
	double[] myWallMag = new double[4];
	double[] myWallExp = new double[4];

	HashMap<String, Boolean> myToggles = new HashMap<String, Boolean>();
	private Springies springies;
	
	/** Default Constructor.*/
	public EnvironmentForces(Springies springies) {
		this.springies = springies;
		readForces();
		myToggles.put(GRAV, true);
		myToggles.put(VISC, true);
		myToggles.put(COM, true);
	}
	
	/**
	 * Reads in the forces from an xml file
	 * if none exists, defaults are set.
	 * Returns void.
	 */
	private void readForces() {
		File f = new File(ENV);
		if (f.exists()) {
			envXMLReader env = new envXMLReader(ENV);

			myGravDir = env.readGravity()[0];
			myGravMag = env.readGravity()[1] * GRAVADJ;
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
	
	/**
	 * Sets the forces.
	 * Returns void.
	 */
	public void doForces() {
		gravForce();
		viscosityForce();
		COMForce();
	}
	
	/**
	 * Toggles forces on and off.
	 * Returns void.
	 * @param force
	 */
	public void toggle(String force) {
		myToggles.put(force, myToggles.get(force) ^ true);
	}
	
	/**
	 * Sets the force for gravity.
	 * Returns void.
	 */
	public void gravForce() {
		if (myToggles.get(GRAV)) {
			WorldManager.getWorld().setGravity(
					new Vec2((float) (myGravMag * Math.cos(Math
							.toRadians(myGravDir))), (float) (myGravMag * Math
							.cos(Math.toRadians(myGravDir)))));
		} else {
			WorldManager.getWorld().setGravity(new Vec2(0f, 0f));
		}
	}

	/**
	 * Sets the force for visocity.
	 * Returns void.
	 */
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
	
	/**
	 * Sets the force for center of mass.
	 * Returns void.
	 */
	public void COMForce() {
		if (myToggles.get(COM)) {
			for (HashMap<String, Mass> massList : springies.getMassMaps()) {
				int xTotal = 0;
				int yTotal = 0;
				for (Mass m : massList.values()) {
					xTotal += m.x;
					yTotal += m.y;
				}
				int xCOM = xTotal / massList.size();
				int yCOM = yTotal / massList.size();

				for (Mass m : massList.values()) {
					Vec2 force = new Vec2((float) (xCOM - m.x),
							(float) (yCOM - m.y));
					force.normalize();
					force = force.mul((float) (myCMMag / Math.pow(
							Math.sqrt(Math.pow(xCOM - m.getX(), 2)
									+ Math.pow(yCOM - m.y, 2)), myCMExp)));

					m.applyForce(force);
				}
			}
		}
	}
	/**Getter*/
	public double[] getWallMags() {
		return myWallMag;
	}
	
	/**Getter*/
	public double[] getWallExps() {
		return myWallExp;
	}
}