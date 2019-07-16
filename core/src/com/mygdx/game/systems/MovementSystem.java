package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.JumpingComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PositionData;
import com.mygdx.game.models.CollisionResults;
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
	public void processEntity(AbstractEntity movingEntity) {
		MovementComponent md = (MovementComponent)movingEntity.getComponent(MovementComponent.class);
		if (md != null) {
			PositionData pos = (PositionData)movingEntity.getComponent(PositionData.class);
			if (md.offX != 0) {
				pos.prevPos.set(pos.rect);
				if (md.offX > Settings.MAX_MOVEMENT) {
					md.offX = Settings.MAX_MOVEMENT;
					MyGdxGame.p("Max movement hit!");					
				}
				pos.rect.move(md.offX * Gdx.graphics.getDeltaTime(), 0);
				CollisionResults results = game.collisionSystem.collided(movingEntity, md.offX, 0);
				md.offX = 0;
				if (results != null) {
					if (results.blocksMovement) {
						pos.rect.set(pos.prevPos); // Move back
					}
					game.processCollisionSystem.processCollision(movingEntity, results);
				}
			}
			if (md.offY != 0) {
				pos.prevPos.set(pos.rect);
				if (md.offY > Settings.MAX_MOVEMENT) {
					md.offY = Settings.MAX_MOVEMENT;
				}
				pos.rect.move(0, md.offY * Gdx.graphics.getDeltaTime());
				CollisionResults results = game.collisionSystem.collided(movingEntity, 0, md.offY);
				if (results != null) {
					if (results.blocksMovement) {
						pos.rect.set(pos.prevPos); // Move back
					}
					game.processCollisionSystem.processCollision(movingEntity, results);
					if (md.offY < 0) {
						JumpingComponent jc = (JumpingComponent)movingEntity.getComponent(JumpingComponent.class);
						if (jc != null) {
							jc.canJump = true;
						}
					}
					if (md.canFall) {
						// Gravity
						md.offY = 0;
					}
				} else if (pos.rect.top < 0) {
					pos.rect.move(0, Settings.LOGICAL_HEIGHT_PIXELS);
				}
			}
			if (md.canFall) {
				// Gravity
				md.offY -= Settings.GRAVITY;
				if (md.offY < -Settings.MAX_GRAVITY) {
					md.offY = -Settings.MAX_GRAVITY;
				}
			} else {
				md.offY = 0;
			}
		}
	}

}
