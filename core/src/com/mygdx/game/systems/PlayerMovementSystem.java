package com.mygdx.game.systems;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.JumpingComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.UserInputComponent;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class PlayerMovementSystem extends AbstractSystem {

	private MyGdxGame game;
	
	public PlayerMovementSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs);
		
		game = _game;
	}

	
	@Override
	public void process() {
		UserInputComponent uic = (UserInputComponent)game.playersAvatar.getComponent(UserInputComponent.class);
		MovementComponent mc = (MovementComponent)game.playersAvatar.getComponent(MovementComponent.class);
		
		if (uic.moveLeft) {
			mc.offX = -Settings.PLAYER_SPEED;
		} else if (uic.moveRight) {
			mc.offX = Settings.PLAYER_SPEED;
		}
		
		if (uic.jump) {
			JumpingComponent jc = (JumpingComponent)game.playersAvatar.getComponent(JumpingComponent.class);
			if (jc.canJump) {
				mc.offY = Settings.JUMP_FORCE;
				jc.canJump = false;
			} else {
				//MyGdxGame.p("Cannot jump!");
			}
		}
	}
	
}
