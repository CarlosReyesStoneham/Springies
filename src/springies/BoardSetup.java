package springies;

import java.util.ArrayList;

import jboxGlue.Wall;
import jgame.JGColor;

public class BoardSetup {
	private ArrayList<Wall> wallList;
	public Springies springies;
	
	public BoardSetup(Springies springies, ArrayList<Wall> wallList) {
		this.springies = springies;
		this.wallList = wallList;
	}
	
	public void setWalls(int area) {
		double wall_margin = area;
		
		final double WALL_THICKNESS = 10;
		final double WALL_WIDTH = springies.displayWidth() - wall_margin * 2
				+ WALL_THICKNESS;
		final double WALL_HEIGHT = springies.displayHeight() - wall_margin * 2
				+ WALL_THICKNESS;

		Wall wall = new Wall("wall", 2, JGColor.green, WALL_WIDTH,
				WALL_THICKNESS);
		wall.setPos(springies.displayWidth() / 2, wall_margin);
		wallList.add(wall);
		
		wall = new Wall("wall", 2, JGColor.green, WALL_WIDTH, WALL_THICKNESS);
		wall.setPos(springies.displayWidth() / 2, springies.displayHeight() - wall_margin);
		wallList.add(wall);
		
		wall = new Wall("wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT);
		wall.setPos(wall_margin, springies.displayHeight() / 2);
		wallList.add(wall);

		wall = new Wall("wall", 2, JGColor.green, WALL_THICKNESS, WALL_HEIGHT);
		wallList.add(wall);
		wall.setPos(springies.displayWidth() - wall_margin,springies.displayHeight() / 2);
		
	}
}
