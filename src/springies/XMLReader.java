package springies;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import jboxGlue.Mass;
import jboxGlue.Muscle;
import jboxGlue.Spring;

public class XMLReader {
	// TODO: Once sure that removeObjects works, remove the ArrayList return for springs and muscles
	private final static int WALLMAG =4;
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
	/*
	 * Create a hashmap with each unique mass object
	 */
	public HashMap<String, Mass> makeMasses(){
		XMLMassMaker xmlmassmaker = new XMLMassMaker(xmlFile, myMassMap, myDoc);
		return xmlmassmaker.makeMassObjects();
	}
	
	public ArrayList<Spring> makeSprings(){
		XMLSpringMaker springmaker = new XMLSpringMaker(xmlFile, myMassMap, myDoc);
		return springmaker.makeSpringObjects();
	}
	
	public ArrayList<Spring> makeMuscles(){
		Mass one;
		Mass two;
		double restLength;
		double constant;
		double amplitude;
		ArrayList<Spring> muscles = new ArrayList<Spring>();
		
		//myDocument doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName("muscle");
		for (int i = 0; i < nodes.getLength(); i++) {
			restLength = -1;
			constant = 1;
					
			Node springItem = nodes.item(i);
			NamedNodeMap nodeMap = springItem.getAttributes();
			
			one = myMassMap.get(nodeMap.getNamedItem("a").getNodeValue());
			two = myMassMap.get(nodeMap.getNamedItem("b").getNodeValue());
			
			amplitude = Double.parseDouble(nodeMap.getNamedItem("amplitude").getNodeValue());
			
			try{restLength = Double.parseDouble(nodeMap.getNamedItem("restlength").getNodeValue());}
			catch(Exception e){}

			try{constant = Double.parseDouble(nodeMap.getNamedItem("constant").getNodeValue());}
			catch(Exception e){}
			
			if(restLength == -1){
				muscles.add(new Muscle(one, two, amplitude));
			}
			else{
				muscles.add(new Muscle(one, two, restLength, constant, amplitude));
			}
		}
		return muscles;
	}
	
	public double[] readGravity(){
		//Document doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName("gravity");
		
		Node item = nodes.item(0);
		NamedNodeMap nodeMap = item.getAttributes();
		
		double direction = Double.parseDouble(nodeMap.getNamedItem("direction").getNodeValue());
		double magnitude = Double.parseDouble(nodeMap.getNamedItem("magnitude").getNodeValue());
		double ret[] = {direction, magnitude};
		return ret;
	}
		
	public double readViscosity(){
		//Document doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName("viscosity");
		
		Node item = nodes.item(0);
		NamedNodeMap nodeMap = item.getAttributes();
		
		return Double.parseDouble(nodeMap.getNamedItem("magnitude").getNodeValue());
	}
	
	public double[] readcm(){
		//Document doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName("centermass");
		
		Node item = nodes.item(0);
		NamedNodeMap nodeMap = item.getAttributes();
		double magnitude = Double.parseDouble(nodeMap.getNamedItem("magnitude").getNodeValue());	
		double exponent = Double.parseDouble(nodeMap.getNamedItem("exponent").getNodeValue());
		double ret[] = {magnitude, exponent};
		return ret;
	}
	
	public double[] readWallMag(){
		//Document doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName("wall");
		double ret[] = new double[WALLMAG];
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			ret[i] = Double.parseDouble(nodeMap.getNamedItem("magnitude").getNodeValue())*.0001;
		}
		return ret;
	}
	
	public double[] readWallExp(){
		//Document doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName("wall");
		double ret[] = new double[4];
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			ret[i] = Double.parseDouble(nodeMap.getNamedItem("exponent").getNodeValue());
		}
		return ret;
	}	
}
