package xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.*;

/**
 * Read in XML files and puts appropriate information
 * about masses into arrays.
 * @author carlosreyes and leevianagray
 *
 */
public abstract class XMLReader {
	
	protected static final String FIXED = "fixed";
	protected static final String ID = "id";
	protected static final String MASS = "mass";
	protected static final String VX = "vx";
	protected static final String VY = "vy";
	protected static final String X = "x";
	protected static final String Y = "y";
	protected static final String A = "a";
	protected static final String B = "b";
	protected static final String CONST = "constant";
	protected static final String REST = "restlength";
	protected static final String SPRING = "spring";
	protected static final String AMP = "amplitude";
	protected static final String MUSC = "muscle";
	
	protected static String xmlFile;

	public XMLReader(String xmlFile) {
		XMLReader.xmlFile = xmlFile;
	}

	/**
	 * Reads in doc, first checking to ensure the file exists.
	 * Makes a Document object from the xml file, otherwise
	 * throws an error.
	 */
	public static Document docIn() {
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
	
	
	
}
