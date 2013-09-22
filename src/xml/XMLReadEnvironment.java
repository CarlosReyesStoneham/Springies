package xml;

import java.util.HashMap;
import jboxGlue.Mass;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReadEnvironment extends XMLReader{

	HashMap<String, Mass> myMassMap;

	Document doc;
	public XMLReadEnvironment(String xmlFile, HashMap<String, Mass> myMassMap, Document doc) {
		super(xmlFile);
		this.myMassMap = myMassMap;
		this.doc = doc;
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
