package xmlFactory;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jboxGlue.Mass;
import jboxGlue.MovableMass;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import xml.XMLReader;

public class BuildAssembly extends XMLReader{
	HashMap<String, Mass> myMassMap;
	Document myDoc;
	Assembly assembly;

	public BuildAssembly(String xmlFile) {
		super(xmlFile);
		this.myDoc = docIn();
		myMassMap = new HashMap<String, Mass>();
	}
	
	public static Document docIn() {
		System.out.println(xmlFile);
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
		float x = 0;
		float y = 0;
		float mass;
		double vx;
		double vy;

		NodeList nodes = myDoc.getElementsByTagName(MASS);
		for (int i = 0; i < nodes.getLength(); i++) {
			vx = 0;
			vy = 0;
			mass = 5f;
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			x = Float.parseFloat(nodeMap.getNamedItem(X).getNodeValue());
			y = Float.parseFloat(nodeMap.getNamedItem(Y).getNodeValue());	
			getNodeMapItems(mass, vx, vy, nodeMap, VX, VY);
			myMassMap.put(nodeMap.getNamedItem(ID).getNodeValue(),
					new MovableMass(x, y, vx, vy, mass));
		}
		nodes = myDoc.getElementsByTagName(FIXED);
		floatParsing(x, y, nodes);
		return myMassMap;
	}

	public void build(AssemblyType type) {
		Mass one;
		Mass two;
		double restLength;
		double constant;
		NodeList nodes = myDoc.getElementsByTagName(SPRING);
		for (int i = 0; i < nodes.getLength(); i++) {
			restLength = -1;
			constant = 1;
			Node springItem = nodes.item(i);
			NamedNodeMap nodeMap = springItem.getAttributes();
			one = myMassMap.get(nodeMap.getNamedItem(A).getNodeValue());
			two = myMassMap.get(nodeMap.getNamedItem(B).getNodeValue());
			getNodeMapItems(0, restLength, constant, nodeMap, REST, CONST);
			if (restLength == -1) {
				//make
				AssemblyFactory.buildAssembly(type).make();
			} else {
				//makefull
				AssemblyFactory.buildAssembly(type).makeFull();
			}
		}
	}
	
	public void getNodeMapItems(double z, double x, double y, NamedNodeMap nodeMap, String first, String second){
		try {
			z = Float.parseFloat(nodeMap.getNamedItem(MASS)
					.getNodeValue());
		} catch (Exception e) {}
		try {
			x = Double.parseDouble(nodeMap.getNamedItem(first)
					.getNodeValue());
		} catch (Exception e) {}
		try {
			y = Double.parseDouble(nodeMap.getNamedItem(second)
					.getNodeValue());
		} catch (Exception e) {}
	}
	
	public void floatParsing(float x, float y, NodeList nodes) {
		for (int i = 0; i < nodes.getLength(); i++) {
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			x = Float.parseFloat(nodeMap.getNamedItem(X).getNodeValue());
			y = Float.parseFloat(nodeMap.getNamedItem(Y).getNodeValue());
			myMassMap.put(nodeMap.getNamedItem(ID).getNodeValue(), new Mass(x,
					y, 0));
		}
	}
}
