package com.mygdx.game.systems;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.JumpingComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.UserInputComponent;
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
	public Class getEntityClass() {
		return UserInputComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity player) {
		UserInputComponent uic = (UserInputComponent)player.getComponent(UserInputComponent.class);
		if (uic != null) {
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
					game.sfx.play("BonusCube.ogg");
					mc.offY = Settings.JUMP_FORCE;
					jc.canJump = false;
				} else {
					//MyGdxGame.p("Cannot jump!");
				}
			}
		}
	}


}