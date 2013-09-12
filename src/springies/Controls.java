package springies;

import jgame.platform.JGEngine;

public class Controls{
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
			yGravity += 0.02;
		}
		if(game.getKey(JGEngine.KeyDown)){
			yGravity -= 0.02;
		}
		if(game.getKey(JGEngine.KeyLeft)){
			xGravity -= 0.02;

		}
		if(game.getKey(JGEngine.KeyRight)){
			xGravity += 0.02;
		}
	}
}
