package springies;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import jboxGlue.Mass;
import jboxGlue.MovableMass;
import jboxGlue.Spring;
import jboxGlue.PhysicalObject;
import jboxGlue.Wall;
import jboxGlue.WorldManager;
import jgame.JGColor;
import jgame.platform.JGEngine;
import org.jbox2d.common.Vec2;
import springies.XMLReader;

@SuppressWarnings("serial")
public class Springies extends JGEngine {

	// Array of massLists
	ArrayList<HashMap<String, Mass>> massMaps = new ArrayList<HashMap<String, Mass>>();
//	HashMap<String, Mass> massList = new HashMap<String, Mass>();

	// Array of Springs... only used for clearing :\
	ArrayList<ArrayList<Spring>> springArrays = new ArrayList<ArrayList<Spring>>();
	
	// TODO: We should make an environment
	// change a lot of these to floats so I don't need to keep changing the type
	// when creating Vec2s
	double gravDir;
	double gravMag;
	double viscosity;
	double cmMag;
	double cmExp;
	double[] wallMag = new double[4];
	double[] wallExp = new double[4];

	public Springies() {
		// set the window size
		int height = 600;
		double aspect = 16.0 / 9.0;
		initEngine((int) (height * aspect), height);
	}

	@Override
	public void initCanvas() {
		setCanvasSettings(1, // width of the canvas in tiles
				1, // height of the canvas in tiles
				displayWidth(), // width of one tile
				displayHeight(), // height of one tile
				null,// foreground colour -> use default colour white
				null,// background colour -> use default colour black
				null // standard font -> use default font
		);
	}

	@Override
	public void initGame() {
		setFrameRate(60, 2);

		WorldManager.initWorld(this);

		final double WALL_MARGIN = 10;
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = displayWidth() - WALL_MARGIN * 2
				+ WALL_THICKNESS;
		final double WALL_HEIGHT = displayHeight() - WALL_MARGIN * 2
				+ WALL_THICKNESS;
				
		File f = new File("src/springies/environment.xml");
		if(f.exists()){
			XMLReader env = new XMLReader("src/springies/environment.xml");
			
			gravDir = env.readGravity()[0];
			gravMag = env.readGravity()[1] * .00001; //Adjusting gravity
			viscosity = env.readViscosity();
			cmMag = env.readcm()[0];
			cmExp = env.readcm()[1];
			wallMag = env.readWallMag();
			wallExp = env.readWallExp();	
		}
		else{
			gravDir = 0;
			gravMag = 0;
			viscosity = 1;
			cmMag = 0;
			cmExp = 0;
			wallMag = new double[4];
			wallExp = new double[4];
		}
		
		PhysicalObject wall = new Wall(this, "wall", 2, JGColor.green, WALL_WIDTH,
				WALL_THICKNESS, wallMag[0], wallExp[0]);
		wall.setPos(displayWidth() / 2, WALL_MARGIN);
		wall = new Wall(this, "wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS, wallMag[2], wallExp[2]);
		wall.setPos(displayWidth() / 2, displayHeight() - WALL_MARGIN);
		wall = new Wall(this, "wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT, wallMag[3], wallExp[3]);
		wall.setPos(WALL_MARGIN, displayHeight() / 2);
		wall = new Wall(this, "wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT, wallMag[1], wallExp[1]);
		wall.setPos(displayWidth() - WALL_MARGIN, displayHeight() / 2);

		makeAssembly();

	}

	@Override
	public void doFrame() {
		checkUserInput();
		// set gravity... I'm going to assume that 0 is normal and that it goes
		// clockwise
			
		WorldManager.getWorld().setGravity(new Vec2((float) (gravMag * Math.cos(90)), (float) (gravMag * Math.sin(gravDir))));
		
		// update game objects
		WorldManager.getWorld().step(1f, 1);
		moveObjects();
		checkCollision(2, 1);
		for(HashMap<String, Mass> massList: massMaps){
		for (Mass m : massList.values()) {
		/*	// walls repel
	//		for (int i = 0; i < 4; i++) {
				// 0 is top wall, 1 is right etc.
				m.applyForce(new Vec2((float) (wallMag[1] / Math.pow(-pfWidth()
						- m.x, wallExp[1])), 0));
				m.applyForce(new Vec2(0, (float) (wallMag[2] / Math.pow(
						-pfHeight() - m.y, wallExp[2]))));
				m.applyForce(new Vec2( (float) (wallMag[3] / Math.pow(m.x,
						wallExp[3])), 0)); 
		
	///			System.out.println(m.x + ", " + m.y);
	//			System.out.println(wallMag[0] + " | " + m.y + " | " + wallExp[0]);
				double trees = m.y;
				double numz =  Math.pow(m.y, wallExp[0]);
				if (!Double.isNaN(numz)) {
					Vec2 lolz = new Vec2(0, (float) (wallMag[0] / numz ));
					m.applyForce(lolz);
				}*/
			
			// viscosity - resistive force on masses proportional to their
			// velocity
			m.yspeed = m.yspeed * viscosity;
			m.xspeed = m.xspeed * viscosity;
		}
		}
	}
	
	private void checkUserInput() {
		if (getKey('N')){
			clearKey('N');
			makeAssembly();
		}
		if (getKey('C')){
			clearKey('C');
/*			for(HashMap<String, Mass> massList : massLists){
				for(Mass m : massList.values()){
					m.remove();
				}
			}
			massLists.clear();
		//	removeObjects(null, 1);
		//	MovableMass m = new MovableMass(100,100,1);
		//	m.remove();
			System.out.println("Moving on.");*/		
			for (ArrayList<Spring> springArray : springArrays) {
				for (Spring s : springArray) {
					s.remove();
				}
			}
			
			for (HashMap<String, Mass> massList : massMaps) {
				for (Mass m : massList.values()) {
					m.remove();
				}
			}

			massMaps.clear();
			springArrays.clear();

		}
	}

	private void makeAssembly() {
		FileDialog selector = new FileDialog(new Frame());
		selector.setVisible(true);
		XMLReader reader = new XMLReader("src/springies/" + selector.getFile());
		massMaps.add(reader.makeMasses());
		for(HashMap<String, Mass> massList: massMaps){
			for (Mass m : massList.values()) {
				System.out.println(m.y);
			}
		}
		reader.makeSprings();
		reader.makeMuscles();
	}

	public void centerOfMass() {
		float topX = 0;
		float topY = 0;
		float totalMass = 500;
		// cmMag, cmExp
		for(HashMap<String, Mass> massList: massMaps){
			for (Mass m : massList.values()) {
				topX += (float) (m.getMyMass()*m.x);
			//	System.out.println(topX);
				topY += (float) (m.getMyMass()*m.y);
			}
		}
		float xCoord = (float) (topX/totalMass);
		float yCoord = (float) (topY/totalMass);
		new Mass(xCoord, yCoord, 1);
	}

	@Override
	public void paintFrame() {
		if(massMaps.isEmpty()){
			setColor(JGColor.white);
			drawString("Press 'N' to load a new assembly",
					viewWidth() / 2, 10, 0);			
		}

	}
	
	public ArrayList<HashMap<String, Mass>> getMassMaps(){
		return massMaps;
	}
}
