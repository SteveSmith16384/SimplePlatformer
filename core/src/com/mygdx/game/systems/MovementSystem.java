package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.JumpingComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.PlayersAvatarComponent;
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
	public Class getEntityClass() {
		return MovementComponent.class;
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
				} else {
					if (pos.rect.centerX() < 0) {
						pos.rect.move(Settings.LOGICAL_WIDTH_PIXELS, 0);
					} else if (pos.rect.centerX() > Settings.LOGICAL_WIDTH_PIXELS) {
						pos.rect.move(-Settings.LOGICAL_WIDTH_PIXELS, 0);
					}
				}
			}
			if (md.offY != 0) {
				pos.prevPos.set(pos.rect);
				float totalDist = md.offY * Gdx.graphics.getDeltaTime();
				if (Math.abs(totalDist) > Settings.MAX_MOVEMENT) {
					MyGdxGame.p("Max movement hit!");					
					totalDist = Settings.MAX_MOVEMENT * Math.signum(totalDist);
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
				} else {
					if (md.offY < 0) {
						JumpingComponent jc = (JumpingComponent)movingEntity.getComponent(JumpingComponent.class);
						if (jc != null) {
							jc.canJump = false;
						}
					}

					if (pos.rect.top < 0) { // Fallen off bottom of screen
						//pos.rect.move(0, Settings.LOGICAL_HEIGHT_PIXELS);
						PlayersAvatarComponent dbm = (PlayersAvatarComponent)movingEntity.getComponent(PlayersAvatarComponent.class);
						if (dbm != null) {
							game.processCollisionSystem.playerKilled(movingEntity, -1);
						}
					}
				}
			}
			if (md.canFall) {
				// Gravity
				if (!Settings.TURN_OFF_GRAVITY) {
					md.offY -= Settings.GRAVITY;
				}
			} else {
				md.offY = 0;
			}
		}
	}

}
