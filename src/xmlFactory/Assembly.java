package xmlFactory;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import jboxGlue.Mass;
import org.w3c.dom.Document;

public abstract class Assembly {
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
	protected HashMap<String, Mass> myMassMap;
	
	protected Mass one;
	protected Mass two;
	protected double restLength;
	protected double constant;
	protected double amplitude;

	private AssemblyType type = null;
	
	public Assembly(AssemblyType type) {
		this.type = type;
		this.myMassMap = new HashMap<String, Mass>();
	}
	

	
	public AssemblyType getType() {
		return type;
	}

	public void setType(AssemblyType type) {
		this.type = type;
	}
	
	public abstract void make();

	public abstract void makeFull();
}
