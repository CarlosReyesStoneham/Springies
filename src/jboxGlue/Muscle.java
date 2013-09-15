package jboxGlue;

public class Muscle extends Spring {
	private double myOriginalLength;
	private double myAmplitude;
	private int period = 60;
	private int count = 0;
	
	public Muscle(Mass first, Mass second, double amplitude) {
		super(first, second);
		myOriginalLength = super.getLength();
		myAmplitude = amplitude;
	}

	public Muscle(Mass first, Mass second, double length, double k, double amplitude) {
		super(first, second, length, k);
		myOriginalLength = length;
		myAmplitude = amplitude;
	}
	
	@Override
	public void move() {
		this.setLength(myOriginalLength*myAmplitude);
		Math.cos(count*2*Math.PI/period);
		count++;
	}
}
