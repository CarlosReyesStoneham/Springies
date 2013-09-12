package springies;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import jboxGlue.Spring;
import jboxGlue.Mass;


public class XML2 {
 
	public static void main(String[] args) {

		File file = new File("src/springies/ball.xml");

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(file);

			getMass(doc);
			//getSpring(doc);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getMass(Document doc) {
		HashMap<String, ArrayList<Integer>> massMap = new HashMap<String, ArrayList<Integer>>();
		NodeList nodes = doc.getElementsByTagName("mass");
		for(int i=0; i < nodes.getLength(); i++){
			
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			for (int j = 0; j < nodeMap.getLength(); j+=3) {
				ArrayList<Integer> xyList = new ArrayList<Integer>();
				Node node = nodeMap.item(j);
				Node node2 = nodeMap.item(j+1);
				Node node3 = nodeMap.item(j+2);
				
				String massid = node.getNodeValue();
				int x = Integer.parseInt(node2.getNodeValue());
				int y = Integer.parseInt(node3.getNodeValue());
				
				xyList.add(x);
				xyList.add(y);
				massMap.put(massid, xyList);

			}
		}
	}
	
	public static void getSpring(Document doc) {
		HashMap<String, ArrayList<Integer>> springMap = new HashMap<String, ArrayList<Integer>>();

		NodeList nodes = doc.getElementsByTagName("spring");
		for(int i=0; i < nodes.getLength(); i++){
			
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();
			for (int j = 0; j < nodeMap.getLength(); j+=3) {
				ArrayList<Integer> xyList = new ArrayList<Integer>();
				Node node = nodeMap.item(j);
				Node node2 = nodeMap.item(j+1);
				Node node3 = nodeMap.item(j+2);
				
				String massid = node.getNodeValue();
				int x = Integer.parseInt(node2.getNodeValue());
				int y = Integer.parseInt(node3.getNodeValue());
				
				xyList.add(x);
				xyList.add(y);
				
				springMap.put(massid, xyList);
				//Spring spring = new Spring(null, null, double, double);
			}
		}
	}
}