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
public class XMLReader {
	static String xmlFile;

	public XMLReader(String xmlFile) {
		XMLReader.xmlFile = xmlFile;
	}

	/**Reads in doc*/
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
