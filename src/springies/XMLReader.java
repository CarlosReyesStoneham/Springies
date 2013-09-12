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
	ArrayList<Spring> mySpringList = new ArrayList<Spring>();

	
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
			for (int j = 0; j < nodeMap.getLength(); j += 3) {
				// Node node = nodeMap.item(j);
				Node node2 = nodeMap.item(j + 1);
				Node node3 = nodeMap.item(j + 2);

				// String massid = node.getNodeValue();
				int x = Integer.parseInt(node2.getNodeValue());
				int y = Integer.parseInt(node3.getNodeValue());

				MovableMass mass = new MovableMass(x, y);
				myMassList.add(mass);
			}
		}
//		for(MovableMass m : myMassList){
//			System.out.println(m);
//		}

		return myMassList;
	}

	public ArrayList<Spring> getSpring(Document doc, ArrayList<MovableMass> massList) {
		NodeList nodes = doc.getElementsByTagName("spring");
		for (int i = 0; i < nodes.getLength(); i++) {

			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			for (int j = 0; j <= nodeMap.getLength(); j += 3) {
				Node node = nodeMap.item(j);
				Node node2 = nodeMap.item(j + 1);
				int m1 = Integer.parseInt(node.getNodeValue().substring(1));
				int m2 = Integer.parseInt(node2.getNodeValue().substring(1));

				MovableMass mass1 = massList.get(m1);
				MovableMass mass2 = massList.get(m2);

				Spring spring = new Spring(mass1, mass2);
				mySpringList.add(spring);
			}
		}
		return mySpringList;
	}
}