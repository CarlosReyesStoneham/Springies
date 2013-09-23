package xml;

import java.util.HashMap;

import jboxGlue.Mass;
import jboxGlue.MovableMass;
import jboxGlue.Muscle;
import jboxGlue.Spring;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author carlosreyes and leevianagray
 */
public class objectXMLReader extends XMLReader {
	private static final String FIXED = "fixed";
	private static final String ID = "id";
	private static final String MASS = "mass";
	private static final String VX = "vx";
	private static final String VY = "vy";
	private static final String X = "x";
	private static final String Y = "y";

	private static final String A = "a";
	private static final String B = "b";
	private static final String CONST = "constant";
	private static final String REST = "restlength";
	private static final String SPRING = "spring";
	private static final String AMP = "amplitude";
	private static final String MUSC = "muscle";

	HashMap<String, Mass> myMassMap;
	Document myDoc;

	public objectXMLReader(String xmlFile) {
		super(xmlFile);
		this.myDoc = docIn();
		myMassMap = new HashMap<String, Mass>();
	}

	public HashMap<String, Mass> makeMasses() {
		float x;
		float y;
		float mass;
		double vx;
		double vy;

		NodeList nodes = myDoc.getElementsByTagName(MASS);
		for (int i = 0; i < nodes.getLength(); i++) {
			// Default values
			vx = 0;
			vy = 0;
			mass = 5f;

			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();

			x = Float.parseFloat(nodeMap.getNamedItem(X).getNodeValue());
			y = Float.parseFloat(nodeMap.getNamedItem(Y).getNodeValue());
			try {
				mass = Float.parseFloat(nodeMap.getNamedItem(MASS)
						.getNodeValue());
			} catch (Exception e) {
			}

			try {
				vx = Double
						.parseDouble(nodeMap.getNamedItem(VX).getNodeValue());
			} catch (Exception e) {
			}

			try {
				vy = Double
						.parseDouble(nodeMap.getNamedItem(VY).getNodeValue());
			} catch (Exception e) {
			}

			myMassMap.put(nodeMap.getNamedItem(ID).getNodeValue(),
					new MovableMass(x, y, vx, vy, mass));
		}

		nodes = myDoc.getElementsByTagName(FIXED);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node massItem = nodes.item(i);
			NamedNodeMap nodeMap = massItem.getAttributes();

			x = Float.parseFloat(nodeMap.getNamedItem(X).getNodeValue());
			y = Float.parseFloat(nodeMap.getNamedItem(Y).getNodeValue());

			myMassMap.put(nodeMap.getNamedItem(ID).getNodeValue(), new Mass(x,
					y, 0));
		}
		return myMassMap;
	}

	public void makeSprings() {
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

			try {
				restLength = Double.parseDouble(nodeMap.getNamedItem(REST)
						.getNodeValue());
			} catch (Exception e) {
			}

			try {
				constant = Double.parseDouble(nodeMap.getNamedItem(CONST)
						.getNodeValue());
			} catch (Exception e) {
			}

			if (restLength == -1) {
				new Spring(one, two, constant);
			} else {
				new Spring(one, two, restLength, constant);
			}
		}
	}

	public void makeMuscles() {
		Mass one;
		Mass two;
		double restLength;
		double constant;
		double amplitude;

		NodeList nodes = myDoc.getElementsByTagName(MUSC);
		for (int i = 0; i < nodes.getLength(); i++) {
			restLength = -1;
			constant = 1;

			Node springItem = nodes.item(i);
			NamedNodeMap nodeMap = springItem.getAttributes();

			one = myMassMap.get(nodeMap.getNamedItem(A).getNodeValue());
			two = myMassMap.get(nodeMap.getNamedItem(B).getNodeValue());

			amplitude = Double.parseDouble(nodeMap.getNamedItem(AMP)
					.getNodeValue());

			try {
				restLength = Double.parseDouble(nodeMap.getNamedItem(REST)
						.getNodeValue());
			} catch (Exception e) {
			}

			try {
				constant = Double.parseDouble(nodeMap.getNamedItem(CONST)
						.getNodeValue());
			} catch (Exception e) {
			}

			if (restLength == -1) {
				new Muscle(one, two, amplitude);
			} else {
				new Muscle(one, two, restLength, constant, amplitude);
			}
		}
	}
}
