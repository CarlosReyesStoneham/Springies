package jboxGlue;

/**
 * Muscles are quite similar to springs, however they
 * have their own energy source. This class depends on
 * the Spring class and modifies the spring to enable
 * autonomous motion.
 * @author carlosreyes and leevianagray
 *
 */
public class Muscle extends Spring {
	private double myOriginalLength;
	private double myAmplitude;
	private final static int period = 60;
	private int count = 0;

	/** Default Constructor. */
	public Muscle(Mass first, Mass second, double amplitude) {
		super(first, second);
		myOriginalLength = super.getLength();
		myAmplitude = amplitude;
	}
	
	/**
	 * Second constructor used when a specific spring constant and
	 * length is given.
	 */
	public Muscle(Mass first, Mass second, double length, double k,
			double amplitude) {
		super(first, second, length, k);
		myOriginalLength = length;
		myAmplitude = amplitude;
	}
	
	/**
	 * Move allows the muscles to move autonomously based 
	 * on a cosine function to give smooth varying movement.
	 * Returns void.
	 */
	@Override
	public void move() {
		this.setLength(myOriginalLength * myAmplitude);
		Math.cos(count * 2 * Math.PI / period);
		count++;
	}
}
