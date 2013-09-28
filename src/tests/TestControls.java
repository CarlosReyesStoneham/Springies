package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import control.ControlWallOut;
import control.Controls;
import springies.BoardSetup;
import springies.Springies;
import xml.objectXMLReader;

public class TestControls {
	
	@Test
	public void testMakeMasses() {
		Springies springies = new Springies();
		objectXMLReader reader = new objectXMLReader("src/xmlfiles/ball.xml");
		springies.addMassMap(reader.makeMasses());
		assertEquals(springies.massMaps.get(0).get("m1").getX(), 325, 0);
		assertEquals(springies.massMaps.get(0).get("m48").getX(), 346, 0);

	}
	
	@Test
	public void testMoveWallIn() {
		Springies springies = new Springies();
		Controls controls = new Controls(springies, null);
		ControlWallOut wallOut = new ControlWallOut(controls.mySpringies, controls.myBoardSetup, controls.myEnvForces);
		wallOut.moveWall();
		System.out.println(BoardSetup.wall_margin);
		assertEquals(BoardSetup.wall_margin+10, 20);
	}
	
	@Test
	public void testMoveWallOut() {
		
	}
	
	@Test
	public void testHelperMethod() {
		
	}
	
	@Test
	public void testKeyUpRegisters() {
		
	}
	
	@Test
	public void testKeyDownRegisters() {
		
	}
	

}
