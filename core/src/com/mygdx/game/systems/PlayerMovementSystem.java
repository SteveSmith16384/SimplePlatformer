package com.mygdx.game.systems;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.JumpingComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PlayersAvatarComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class PlayerMovementSystem extends AbstractSystem {

	private MyGdxGame game;

	public PlayerMovementSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs, PlayersAvatarComponent.class);

		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity player) {
		PlayersAvatarComponent uic = (PlayersAvatarComponent)player.getComponent(PlayersAvatarComponent.class);
		if (uic != null) {
			MovementComponent mc = (MovementComponent)player.getComponent(MovementComponent.class);

			if (uic.moveLeft) {
				mc.offX = -Settings.PLAYER_SPEED;
			} else if (uic.moveRight) {
				mc.offX = Settings.PLAYER_SPEED;
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