package com.mygdx.game.systems;

import java.util.List;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.MobComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PositionComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;
import com.scs.lang.NumberFunctions;

public class MobAISystem extends AbstractSystem {

	private MyGdxGame game;

	public MobAISystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs);

		game = _game;
	}


	@Override
	public Class<?> getEntityClass() {
		return MobComponent.class;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		MobComponent mob = (MobComponent)entity.getComponent(MobComponent.class);
		if (mob != null) {
			if (mob.dirX == 0) {
				mob.dirX = NumberFunctions.rnd(-1, 1);
			}

			if (mob.dontWalkOffEdge) {
				PositionComponent pos = (PositionComponent)entity.getComponent(PositionComponent.class);
				// Check for missing floor
				boolean floorFound = false;
				List<AbstractEntity> ents = game.collisionSystem.getEntitiesAt(pos.rect.centerX(), pos.rect.bottom-1); 
				for (AbstractEntity e : ents) {
					CollisionComponent cc = (CollisionComponent)e.getComponent(CollisionComponent.class);
					if (cc.blocksMovement || cc.fluidPlatform) {
						floorFound = true;
						break;
					}
				}
				if (!floorFound) {
					mob.dirX = mob.dirX * -1;
					//game.p("Mob turning: " + mob.dirX);
				}
			}

			MovementComponent move = (MovementComponent)entity.getComponent(MovementComponent.class);
			move.offX = mob.dirX * mob.speed;

		}
	}

}
