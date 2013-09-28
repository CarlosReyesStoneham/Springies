package xmlFactory;

import jboxGlue.Spring;

public class SpringAssembly extends Assembly{
	public SpringAssembly() {
		super(AssemblyType.SPRING);
		make();
		makeFull();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void make() {
		new Spring(one, two, constant);
	}
	
	@Override
	public void makeFull() {
		new Spring(one, two, restLength, constant);
	}
}
