package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.JumpingComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PositionComponent;
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
			PositionComponent pos = (PositionComponent)movingEntity.getComponent(PositionComponent.class);
			//MyGdxGame.p("Mob pos :" + pos.rect);
			if (md.offX != 0) {
				pos.prevPos.set(pos.rect);
				float totalDist = md.offX * Gdx.graphics.getDeltaTime();
				if (Math.abs(totalDist) > Settings.MAX_MOVEMENT) {
					totalDist = Settings.MAX_MOVEMENT * Math.signum(totalDist);
					MyGdxGame.p("Max movement hit!");					
				}
				pos.rect.move(totalDist, 0);
				CollisionResults results = game.collisionSystem.collided(movingEntity, md.offX, 0);
				md.offX = 0;
				if (results != null) {
					if (results.moveBack) {
						pos.rect.set(pos.prevPos); // Move back
					}
					game.processCollisionSystem.processCollision(movingEntity, results);
				}
			}
			if (md.offY != 0) {
				pos.prevPos.set(pos.rect);
				float totalDist = md.offY * Gdx.graphics.getDeltaTime();
				if (Math.abs(totalDist) > Settings.MAX_MOVEMENT) {
					totalDist = Settings.MAX_MOVEMENT * Math.signum(totalDist);
					MyGdxGame.p("Max movement hit!");					
				}
				pos.rect.move(0, totalDist);
				CollisionResults results = game.collisionSystem.collided(movingEntity, 0, md.offY);
				if (results != null) {
					if (results.moveBack) {
						pos.rect.set(pos.prevPos); // Move back
						if (md.offY < 0) {
							JumpingComponent jc = (JumpingComponent)movingEntity.getComponent(JumpingComponent.class);
							if (jc != null) {
								jc.canJump = true;
							}
						}
						if (md.canFall) {
							md.offY = 0;
						}
					}
					game.processCollisionSystem.processCollision(movingEntity, results);
				} else if (pos.rect.top < 0) { // Fallen off bottom of screen, so reappear at the top
					pos.rect.move(0, Settings.LOGICAL_HEIGHT_PIXELS);
				}
			}
			if (md.canFall) {
				// Gravity
				md.offY -= Settings.GRAVITY;
				/*if (md.offY < -Settings.MAX_GRAVITY) {
					md.offY = -Settings.MAX_GRAVITY;
					MyGdxGame.p("Max gravity hit!");					
				}*/
			} else {
				md.offY = 0;
			}
		}
	}

}
