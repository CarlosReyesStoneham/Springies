package jboxGlue;

import org.jbox2d.common.Vec2;

import springies.BoardSetup;
import walls.Wall;

public class HitsWall {
	private static final int HITMARGIN = 15;

	Wall w;
	public HitsWall(Wall w) {
		this.w = w;
	}
	
	public void bounce(Mass m) {
		//right
		if(w.checkCollision(1, 0, 0) == 1  && w.myBody.getPosition().x >= w.myWidth-HITMARGIN) {
			m.myBody.m_linearVelocity = (new Vec2(-1f, 0));
		}
		//left
		if(w.checkCollision(1, 0, 0) == 1  && (w.myBody.getPosition().x <= HITMARGIN+BoardSetup.wall_margin)) {
			m.myBody.m_linearVelocity = (new Vec2(1f, 0));
		}
		//bottom
		if(w.checkCollision(1, 0, 0) == 1  && (w.myBody.getPosition().y >= w.myHeight-HITMARGIN)) {
			m.myBody.m_linearVelocity = (new Vec2(0, -1f));
		}
		//top
		if(w.checkCollision(1, 0, 0) == 1  && (w.myBody.getPosition().y <= HITMARGIN+BoardSetup.wall_margin)) {
			m.myBody.m_linearVelocity = (new Vec2(0, 1f));
		}
	}
}
