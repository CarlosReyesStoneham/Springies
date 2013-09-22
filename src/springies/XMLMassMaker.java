package springies;

import java.util.HashMap;

import jboxGlue.Mass;
import jboxGlue.MovableMass;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLMassMaker extends XMLReader{
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
		
		NodeList nodes = doc.getElementsByTagName("mass");
		for (int i = 0; i < nodes.getLength(); i++) {
			vx = 0;
			vy = 0;
			mass = 5f;
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			
			x = Float.parseFloat(nodeMap.getNamedItem("x").getNodeValue());
			y = Float.parseFloat(nodeMap.getNamedItem("y").getNodeValue());
			try{mass = Float.parseFloat(nodeMap.getNamedItem("mass").getNodeValue());}
			catch(Exception e){}

			try{vx = Double.parseDouble(nodeMap.getNamedItem("vx").getNodeValue());}
			catch(Exception e){}
			
			try{vy = Double.parseDouble(nodeMap.getNamedItem("vy").getNodeValue());}
			catch(Exception e){}
			
			myMassMap.put(nodeMap.getNamedItem("id").getNodeValue(), new MovableMass(x, y, vx, vy, mass));
		}
		
		nodes = doc.getElementsByTagName("fixed");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			
			x = Float.parseFloat(nodeMap.getNamedItem("x").getNodeValue());
			y = Float.parseFloat(nodeMap.getNamedItem("y").getNodeValue());
			
			myMassMap.put(nodeMap.getNamedItem("id").getNodeValue(), new Mass(x, y, 0));
		}
		return myMassMap;
	}
}
