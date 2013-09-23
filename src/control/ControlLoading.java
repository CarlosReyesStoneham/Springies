package control;

import springies.BoardSetup;
import springies.Springies;

/**
 * Control loading enables a user to press either the C or the N
 * key to either (C)lear an assembly or load a (N)ew assembly into
 * the playing field
 * 
 * One could use this class to allow a user to load files with a different
 * key or the mouse.
 * @author carlosreyes and leevianagray
 *
 */
public class ControlLoading {
	private Springies mySpringies;
	private BoardSetup myBoardSetup;
	private static final char N = 'N';
	private static final char C = 'C';
	
	/** Constructor.*/
	public ControlLoading(Springies mySpringies, BoardSetup myBoardSetup) {
		this.mySpringies = mySpringies;
		this.myBoardSetup = myBoardSetup;
	}

	/** Loads in a file when key N is pressed, returns void.*/
	public void load() {
		// Press 'N' to load new assembly
		if (mySpringies.getKey(N)) {
			mySpringies.clearKey(N);
			myBoardSetup.makeAssembly();
		}
	}
	
	/** Clears the playing field when key C is pressed, returns void.*/
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
