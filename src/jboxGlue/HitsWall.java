package jboxGlue;

import org.jbox2d.common.Vec2;

import springies.BoardSetup;

public class HitsWall {
	
	Wall w;
	public HitsWall(Wall w) {
		this.w = w;
	}
	
	public void bounce(Mass m) {
		//right
		if(w.checkCollision(1, 0, 0) == 1  && w.myBody.getPosition().x >= w.myWidth-10) {
			m.myBody.m_linearVelocity = (new Vec2(-1f,0));
		}
		//left
		if(w.checkCollision(1, 0, 0) == 1  && (w.myBody.getPosition().x <= 10+BoardSetup.wall_margin)) {
			m.myBody.m_linearVelocity = (new Vec2(1f, 0));
		}
		//bottom
		if(w.checkCollision(1, 0, 0) == 1  && (w.myBody.getPosition().y >= w.myHeight-10)) {
			m.myBody.m_linearVelocity = (new Vec2(0, -1f));
		}
		//top
		if(w.checkCollision(1, 0, 0) == 1  && (w.myBody.getPosition().y <= 10+BoardSetup.wall_margin)) {
			m.myBody.m_linearVelocity = (new Vec2(0, 1f));
		}
	}
}
