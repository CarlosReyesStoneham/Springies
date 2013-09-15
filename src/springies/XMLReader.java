package springies;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.*;
import java.util.ArrayList;

import jboxGlue.MovableMass;
import jboxGlue.Spring;

public class XMLReader {

	public ArrayList<MovableMass> myMassList = new ArrayList<MovableMass>();
	public ArrayList<Spring> mySpringList = new ArrayList<Spring>();

	
	public Document docIn() {
		File file = new File("src/springies/ball.xml");

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

	public ArrayList<MovableMass> getMass() {
		Document doc = docIn();
		NodeList nodes = doc.getElementsByTagName("mass");
		for (int i = 0; i < nodes.getLength(); i++) {

			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			
			ArrayList<Node> nodeList = new ArrayList<Node>();
			int len = nodeMap.getLength();
			for (int j = 0; j < nodeMap.getLength(); j += len) {
				
				for(int k=0; k <len; k++){
					Node node = nodeMap.item(j + k);
					nodeList.add(node);
					System.out.println(nodeList.get(k));
				}
				
				int x = Integer.parseInt(nodeList.get(1).getNodeValue());
				int y = Integer.parseInt(nodeList.get(2).getNodeValue());
				int xForce = 0; //Integer.parseInt(nodeList.get(3).getNodeValue());
				int yForce = 0; //Integer.parseInt(nodeList.get(4).getNodeValue());
				int m = 0; //Integer.parseInt(nodeList.get(5).getNodeValue());
				MovableMass mass;
				if(len == 3){
					mass = new MovableMass(x, y);
					myMassList.add(mass);

				}
				else if(len == 4){
					xForce = Integer.parseInt(nodeList.get(3).getNodeValue());
					mass = new MovableMass(x, y, xForce, yForce, m);
					myMassList.add(mass);
				}
				else if(len == 5){
					yForce = Integer.parseInt(nodeList.get(4).getNodeValue());
					mass = new MovableMass(x, y, xForce, yForce, m);
					myMassList.add(mass);
				}
				else if(len ==6){
					m = 0;
					mass = new MovableMass(x, y, xForce, yForce, m);
					myMassList.add(mass);

				}
			}
		}

		return myMassList;
	}

	public ArrayList<Spring> getSpring() {
		Document doc = docIn();
		NodeList nodes = doc.getElementsByTagName("spring");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			for (int j = 0; j <= nodeMap.getLength()-4; j += 4) {
				Node node = nodeMap.item(j);
				Node node2 = nodeMap.item(j + 1);
				
				int m1 = Integer.parseInt(node.getNodeValue().substring(1));
				int m2 = Integer.parseInt(node2.getNodeValue().substring(1));

				MovableMass mass1 = myMassList.get(m1-1);
				MovableMass mass2 = myMassList.get(m2-1);
				Spring spring = new Spring(mass1, mass2);
				mySpringList.add(spring);
			}
		}
		return mySpringList;
	}
}