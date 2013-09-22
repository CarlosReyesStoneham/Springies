package xml;

import java.util.ArrayList;
import java.util.HashMap;

import jboxGlue.Mass;
import jboxGlue.Spring;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLSpringMaker extends XMLReader{
	private static final String A = "a";
	private static final String B = "b";
	private static final String CONST = "constant";
	private static final String REST = "restlength";
	private static final String SPRING = "spring";

	HashMap<String, Mass> myMassMap;

	Document doc;
	public XMLSpringMaker(String xmlFile, HashMap<String, Mass> myMassMap, Document doc) {
		super(xmlFile);
		this.myMassMap = myMassMap;
		this.doc = doc;
	}
	public ArrayList<Spring> makeSpringObjects(){
		Mass one;
		Mass two;
		double restLength;
		double constant;
		ArrayList<Spring> springs = new ArrayList<Spring>();
		
		//Document doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName(SPRING);
		for (int i = 0; i < nodes.getLength(); i++) {
			restLength = -1;
			constant = 1;
					
			Node springItem = nodes.item(i);
			NamedNodeMap nodeMap = springItem.getAttributes();
			
			one = myMassMap.get(nodeMap.getNamedItem(A).getNodeValue());
			two = myMassMap.get(nodeMap.getNamedItem(B).getNodeValue());

			try{restLength = Double.parseDouble(nodeMap.getNamedItem(REST).getNodeValue());}
			catch(Exception e){}

			try{constant = Double.parseDouble(nodeMap.getNamedItem(CONST).getNodeValue());}
			catch(Exception e){}
			
			if(restLength == -1){
				springs.add(new Spring(one, two, constant));
			}
			else{
				springs.add(new Spring(one, two, restLength, constant));
			}
		}
		return springs;
	}
}
