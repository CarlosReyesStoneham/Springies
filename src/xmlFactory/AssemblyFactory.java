package xmlFactory;

public class AssemblyFactory {
	public static Assembly buildAssembly(AssemblyType model) {
		Assembly assembly = null;
		switch(model) {
		case MASS:
			assembly = new MassAssembly();
			break;
		case SPRING:
			assembly = new SpringAssembly();
			break;
		case MUSCLE:
			assembly = new MuscleAssembly();
			break;
		default:
			throw new IllegalStateException("Not an implemented assembly");
		}
		return assembly;
	}
}
