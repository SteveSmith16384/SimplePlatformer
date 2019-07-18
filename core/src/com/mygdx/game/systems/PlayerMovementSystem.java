package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.ImageComponent;
import com.mygdx.game.components.JumpingComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.UserInputComponent;
import com.mygdx.game.components.WalkingAnimationComponent;
import com.scs.basicecs.AbstractEntity;
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
		for (AbstractEntity player : game.playersAvatars) {
			UserInputComponent uic = (UserInputComponent)player.getComponent(UserInputComponent.class);
			MovementComponent mc = (MovementComponent)player.getComponent(MovementComponent.class);

			if (uic.moveLeft) {
				mc.offX = -Settings.PLAYER_SPEED;
				//this.checkAnimation(player, -1);
			} else if (uic.moveRight) {
				mc.offX = Settings.PLAYER_SPEED;
				//this.checkAnimation(player, 1);
			} else {
				//this.checkAnimation(player, 0);
			}

			if (uic.jump) {
				JumpingComponent jc = (JumpingComponent)player.getComponent(JumpingComponent.class);
				if (jc.canJump) {
					mc.offY = Settings.JUMP_FORCE;
					jc.canJump = false;
				} else {
					//MyGdxGame.p("Cannot jump!");
				}
			}
		}
	}


}