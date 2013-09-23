package control;

import springies.BoardSetup;
import springies.Springies;

public class ControlLoading {
	private Springies mySpringies;
	private BoardSetup myBoardSetup;
	private static final char N = 'N';
	private static final char C = 'C';

	public ControlLoading(Springies mySpringies, BoardSetup myBoardSetup) {
		this.mySpringies = mySpringies;
		this.myBoardSetup = myBoardSetup;
	}

	public void load() {
		// Press 'N' to load new assembly
		if (mySpringies.getKey(N)) {
			mySpringies.clearKey(N);
			myBoardSetup.makeAssembly();
		}
	}

	public void clear() {
		// Press 'C' to clear assemblies
		if (mySpringies.getKey(C)) {
			mySpringies.clearKey(C);

			mySpringies.removeObjects("Spring", 0);
			mySpringies.removeObjects("Mass", 0);

			mySpringies.clearMassMaps();
		}
	}
}
