package xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import jboxGlue.Mass;
import jboxGlue.Spring;

public class XMLReader {
	// TODO: Once sure that removeObjects works, remove the ArrayList return for springs and muscles
	protected final static int WALLMAG =4;
	HashMap<String, Mass> myMassMap = new HashMap<String, Mass>();
	protected Document myDoc;
	String xmlFile;

	public XMLReader(String xmlFile){
		myDoc = XMLReader.docIn(xmlFile);
		this.xmlFile = xmlFile;
	}

	public static Document docIn(String xmlFile) {
		File file = new File(xmlFile);

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(file);
			return doc;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<String, Mass> makeMasses() {
		XMLMassMaker xmlmassmaker = new XMLMassMaker(xmlFile, myMassMap, myDoc);
		return xmlmassmaker.makeMassObjects();
	}

	public ArrayList<Spring> makeSprings(){
		XMLSpringMaker springmaker = new XMLSpringMaker(xmlFile, myMassMap, myDoc);
		return springmaker.makeSpringObjects();
	}
	
	public ArrayList<Spring> makeMuscles(){
		XMLMuscleMaker musclemaker = new XMLMuscleMaker(xmlFile, myMassMap, myDoc);
		return musclemaker.makeMuscleObjects();
	}
		
	public double[] readGravity(){
		envXMLReader readenvironment = new envXMLReader(xmlFile, myMassMap, myDoc);
		return readenvironment.readGravity();
	}
		
	public double readViscosity(){
		envXMLReader readenvironment = new envXMLReader(xmlFile, myMassMap, myDoc);
		return readenvironment.readViscosity();
	}
	
	public double[] readcm(){
		envXMLReader readenvironment = new envXMLReader(xmlFile, myMassMap, myDoc);
		return readenvironment.readcm();
	}
	
	public double[] readWallMag(){
		envXMLReader readenvironment = new envXMLReader(xmlFile, myMassMap, myDoc);
		return readenvironment.readWallMag();
	}
	
	public double[] readWallExp(){
		envXMLReader readenvironment = new envXMLReader(xmlFile, myMassMap, myDoc);
		return readenvironment.readWallExp();
	}	
}
