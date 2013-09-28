package xmlFactory;

import jboxGlue.Muscle;

public class MuscleAssembly extends Assembly{
	public MuscleAssembly() {
		super(AssemblyType.MUSCLE);
		make();
		makeFull();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void make() {
		new Muscle(one, two, amplitude);
	}
	
	@Override
	public void makeFull() {
		new Muscle(one, two, restLength, constant, amplitude);
	}
}
