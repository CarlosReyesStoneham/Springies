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
				float x = 0; float y = 0; double xForce = 0; double yForce = 0; int m = 0;
				
				for(int k=0; k <len; k++){
					String name = nodeMap.item(j+k).getNodeName();
					if(name.equals("x")){
						x = Float.parseFloat(nodeMap.item(j+k).getNodeValue().toString());
					}
					if(name.equals("y")){
						y = Float.parseFloat(nodeMap.item(j+k).getNodeValue().toString());
					}
					if(name.equals("vx")){
						xForce = Double.parseDouble(nodeMap.item(j+k).getNodeValue().toString());
					}
					if(name.equals("vy")){
						yForce = Double.parseDouble(nodeMap.item(j+k).getNodeValue().toString());
					}
					if(name.equals("mass")){
						m = Integer.parseInt(nodeMap.item(j+k).getNodeValue().toString());
					}
				}
				
				MovableMass mass;
				if(len == 3){
					mass = new MovableMass(x, y);
					myMassList.add(mass);

				}
				else{
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
			
			int len = nodeMap.getLength();
			System.out.println(len);
			for (int j = 0; j <= nodeMap.getLength()-len; j += len) {
				String a = ""; String b = ""; double rest = 0; double constant = 0;
				
				for(int k=0; k <len; k++){
					String name = nodeMap.item(j+k).getNodeName();
					if(name.equals("a")){
						a = (nodeMap.item(j+k).getNodeValue().toString());
					}
					if(name.equals("b")){
						b = (nodeMap.item(j+k).getNodeValue().toString());
					}
					if(name.equals("restlength")){
						rest = Double.parseDouble(nodeMap.item(j+k).getNodeValue().toString());
					}
					if(name.equals("constant")){
						constant = Double.parseDouble(nodeMap.item(j+k).getNodeValue().toString());
					}
				}
				
				int m1 = Integer.parseInt(a.substring(1));
				int m2 = Integer.parseInt(b.substring(1));
				MovableMass mass1 = myMassList.get(m1-1);
				MovableMass mass2 = myMassList.get(m2-1);
				Spring spring;
				
				if(len == 2){
					spring = new Spring(mass1, mass2);
					mySpringList.add(spring);

				}
				else if(len == 3) {
					spring = new Spring(mass1, mass2, rest);
					mySpringList.add(spring);
				}
				else{
					System.out.println(rest);
					spring = new Spring(mass1, mass2, rest, constant);
					mySpringList.add(spring);
				}
			}
		}
		return mySpringList;
	}
}