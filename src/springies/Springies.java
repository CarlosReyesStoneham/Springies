package springies;

import java.awt.FileDialog;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import jboxGlue.Mass;
import jboxGlue.Spring;
import jboxGlue.Wall;
import jboxGlue.WorldManager;
import jgame.platform.JGEngine;
import springies.Controls;

@SuppressWarnings("serial")
public class Springies extends JGEngine {

	// Array of massLists
	ArrayList<HashMap<String, Mass>> massMaps = new ArrayList<HashMap<String, Mass>>();
	// Array of Springs... only used for clearing :\
	ArrayList<ArrayList<Spring>> springArrays = new ArrayList<ArrayList<Spring>>();
	ArrayList<Wall> wallList = new ArrayList<Wall>();

	int initialArea = 10; //Initial wall margin
	int toggleGravity = 0; //Gravity on/off

	BoardSetup boardSet = new BoardSetup(this, wallList);
	EnvironmentForces envForce = new EnvironmentForces(this);
	Controls controller = new Controls(this, boardSet, massMaps, springArrays, wallList);

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

		boardSet.setWalls(initialArea);
		//setWalls(initialArea);
		boardSet.makeAssembly(massMaps, springArrays);
		//fileIn();
		boardSet.fileIn();
	}

	@Override
	public void doFrame() {
		controller.checkUserInput();
		envForce.calculateGravitationalForce(toggleGravity);
		// update game objects
		WorldManager.getWorld().step(1f, 1);
		moveObjects();
		checkCollision(2, 1);
		envForce.wallForce(massMaps);
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

	@Override
	public void paintFrame() {}
	
	public ArrayList<HashMap<String, Mass>> getMassMaps(){
		return massMaps;
	}
}
