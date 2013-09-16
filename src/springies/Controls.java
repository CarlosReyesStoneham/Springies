package springies;

import jgame.platform.JGEngine;

public class Controls{
	private final static double gravAdjust = 0.02;
	Springies game;
	float xGravity;
	float yGravity;
	public Controls(Springies game, float xGravity, float yGravity){
		this.game = game;
		this.xGravity = xGravity;
		this.yGravity = yGravity;
	}
	
	public void changeGravity(){
		if(game.getKey(JGEngine.KeyUp)){
			yGravity += gravAdjust;
		}
		if(game.getKey(JGEngine.KeyDown)){
			yGravity -= gravAdjust;
		}
		if(game.getKey(JGEngine.KeyLeft)){
			xGravity -= gravAdjust;
		}
		if(game.getKey(JGEngine.KeyRight)){
			xGravity += gravAdjust;
		}
	}
}
