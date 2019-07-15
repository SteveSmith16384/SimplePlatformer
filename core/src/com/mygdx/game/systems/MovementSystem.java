package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.JumpingComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PositionData;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class MovementSystem extends AbstractSystem {

	private MyGdxGame game;

	public MovementSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs);

		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		MovementComponent md = (MovementComponent)entity.getComponent(MovementComponent.class);
		if (md != null) {
			PositionData pos = (PositionData)entity.getComponent(PositionData.class);
			if (md.offX != 0) {
				pos.prevPos.set(pos.rect);
				pos.rect.move(md.offX * Gdx.graphics.getDeltaTime(), 0);
				md.offX = 0;
				if (game.collisionSystem.collided(entity)) {
					pos.rect.set(pos.prevPos); // Move back
				}
			}
			if (md.offY != 0) {
				pos.prevPos.set(pos.rect);
				pos.rect.move(0, md.offY * Gdx.graphics.getDeltaTime());
				//md.offX = 0;
				if (game.collisionSystem.collided(entity)) {
					pos.rect.set(pos.prevPos); // Move back
					if (md.offY < 0) {
						JumpingComponent jc = (JumpingComponent)entity.getComponent(JumpingComponent.class);
						if (jc != null) {
							jc.canJump = true;
						}
					}
					if (md.canFall) {
						// Gravity
						md.offY = 0;
					}
				}
			}
			if (md.canFall) {
				// Gravity
				md.offY -= Settings.GRAVITY;
			} else {
				md.offY = 0;
			}
		}
	}

}
