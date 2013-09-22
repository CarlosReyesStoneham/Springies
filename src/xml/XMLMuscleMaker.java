package xml;

import java.util.ArrayList;
import java.util.HashMap;

import jboxGlue.Mass;
import jboxGlue.Muscle;
import jboxGlue.Spring;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLMuscleMaker extends XMLReader{

	HashMap<String, Mass> myMassMap;

	Document doc;
	public XMLMuscleMaker(String xmlFile, HashMap<String, Mass> myMassMap, Document doc) {
		super(xmlFile);
		this.myMassMap = myMassMap;
		this.doc = doc;
	}
	public ArrayList<Spring> makeMuscleObjects(){
		Mass one;
		Mass two;
		double restLength;
		double constant;
		double amplitude;
		ArrayList<Spring> muscles = new ArrayList<Spring>();
		
		//myDocument doc = docIn();
		NodeList nodes = myDoc.getElementsByTagName("muscle");
		for (int i = 0; i < nodes.getLength(); i++) {
			restLength = -1;
			constant = 1;
					
			Node springItem = nodes.item(i);
			NamedNodeMap nodeMap = springItem.getAttributes();
			
			one = myMassMap.get(nodeMap.getNamedItem("a").getNodeValue());
			two = myMassMap.get(nodeMap.getNamedItem("b").getNodeValue());
			
			amplitude = Double.parseDouble(nodeMap.getNamedItem("amplitude").getNodeValue());
			
			try{restLength = Double.parseDouble(nodeMap.getNamedItem("restlength").getNodeValue());}
			catch(Exception e){}

			try{constant = Double.parseDouble(nodeMap.getNamedItem("constant").getNodeValue());}
			catch(Exception e){}
			
			if(restLength == -1){
				muscles.add(new Muscle(one, two, amplitude));
			}
			else{
				muscles.add(new Muscle(one, two, restLength, constant, amplitude));
			}
		}
		return muscles;
	}
}
