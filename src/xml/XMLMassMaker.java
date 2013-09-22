package xml;

import java.util.HashMap;

import jboxGlue.Mass;
import jboxGlue.MovableMass;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLMassMaker extends XMLReader{
	
	private static final String FIXED = "fixed";
	private static final String ID = "id";
	private static final String MASS = "mass";
	private static final String VX = "vx";
	private static final String VY = "vy";
	private static final String X = "x";
	private static final String Y = "y";

	HashMap<String, Mass> myMassMap;

	Document doc;
	public XMLMassMaker(String xmlFile, HashMap<String, Mass> myMassMap, Document doc) {
		super(xmlFile);
		this.myMassMap = myMassMap;
		this.doc = doc;
	}
	public HashMap<String, Mass> makeMassObjects(){
		float x;
		float y;
		float mass;
		double vx;
		double vy;
		
		NodeList nodes = doc.getElementsByTagName(MASS);
		for (int i = 0; i < nodes.getLength(); i++) {
			vx = 0;
			vy = 0;
			mass = 5f;
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			
			x = Float.parseFloat(nodeMap.getNamedItem(X).getNodeValue());
			y = Float.parseFloat(nodeMap.getNamedItem(Y).getNodeValue());
			try{mass = Float.parseFloat(nodeMap.getNamedItem(MASS).getNodeValue());}
			catch(Exception e){}

			try{vx = Double.parseDouble(nodeMap.getNamedItem(VX).getNodeValue());}
			catch(Exception e){}
			
			try{vy = Double.parseDouble(nodeMap.getNamedItem(VY).getNodeValue());}
			catch(Exception e){}
			
			myMassMap.put(nodeMap.getNamedItem(ID).getNodeValue(), new MovableMass(x, y, vx, vy, mass));
		}
		
		nodes = doc.getElementsByTagName(FIXED);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			
			x = Float.parseFloat(nodeMap.getNamedItem(X).getNodeValue());
			y = Float.parseFloat(nodeMap.getNamedItem(Y).getNodeValue());
			
			myMassMap.put(nodeMap.getNamedItem(ID).getNodeValue(), new Mass(x, y, 0));
		}
		return myMassMap;
	}
}
