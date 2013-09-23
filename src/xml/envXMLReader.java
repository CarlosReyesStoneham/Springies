package xml;

import java.util.HashMap;
import jboxGlue.Mass;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class envXMLReader extends XMLReader{
	
	private static final String CENTER = "centermass";
	private static final String DIR = "direction";
	private static final String EXP = "exponent";
	private static final String GRAV = "gravity";
	private static final String MAG = "magnitude";
	private static final String VISC = "viscosity";
	private static final String WALL = "wall";


	HashMap<String, Mass> myMassMap;

	Document doc;
	public envXMLReader(String xmlFile, HashMap<String, Mass> myMassMap, Document doc) {
		super(xmlFile);
		this.myMassMap = myMassMap;
		this.doc = doc;
	}
	public double[] readGravity(){
		//Document doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName(GRAV);
		
		Node item = nodes.item(0);
		NamedNodeMap nodeMap = item.getAttributes();
		
		double direction = Double.parseDouble(nodeMap.getNamedItem(DIR).getNodeValue());
		double magnitude = Double.parseDouble(nodeMap.getNamedItem(MAG).getNodeValue());
		double ret[] = {direction, magnitude};
		return ret;
	}
		
	public double readViscosity(){
		//Document doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName(VISC);
		
		Node item = nodes.item(0);
		NamedNodeMap nodeMap = item.getAttributes();
		
		return Double.parseDouble(nodeMap.getNamedItem(MAG).getNodeValue());
	}
	
	public double[] readcm(){
		//Document doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName(CENTER);
		
		Node item = nodes.item(0);
		NamedNodeMap nodeMap = item.getAttributes();
		double magnitude = Double.parseDouble(nodeMap.getNamedItem(MAG).getNodeValue());	
		double exponent = Double.parseDouble(nodeMap.getNamedItem(EXP).getNodeValue());
		double ret[] = {magnitude, exponent};
		return ret;
	}
	
	public double[] readWallMag(){
		//Document doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName(WALL);
		double ret[] = new double[WALLMAG];
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			ret[i] = Double.parseDouble(nodeMap.getNamedItem(MAG).getNodeValue())*.0001;
		}
		return ret;
	}
	
	public double[] readWallExp(){
		//Document doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName(WALL);
		double ret[] = new double[4];
		
		for (int i = 0; i < nodes.getLength(); i++) {
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			ret[i] = Double.parseDouble(nodeMap.getNamedItem(EXP).getNodeValue());
		}
		return ret;
	}	
}
