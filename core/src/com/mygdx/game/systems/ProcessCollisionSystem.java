package com.mygdx.game.systems;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.CanCollectComponent;
import com.mygdx.game.components.CollectableComponent;
import com.mygdx.game.components.DestroyedByMobComponent;
import com.mygdx.game.components.KillByJumpingComponent;
import com.mygdx.game.components.MobComponent;
import com.mygdx.game.models.CollisionResults;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class ProcessCollisionSystem extends AbstractSystem {

	private MyGdxGame game;

	public ProcessCollisionSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs);

		game = _game;
	}


	public void processCollision(AbstractEntity mover, CollisionResults results) {
		// Player moving into mob
		{
			MobComponent mob = (MobComponent)results.collidedWith.getComponent(MobComponent.class);
			if (mob != null) {
				// Player jumping on mob
				if (results.fromAbove) {
					KillByJumpingComponent kbj = (KillByJumpingComponent)mover.getComponent(KillByJumpingComponent.class);
					if (kbj != null) {
						results.collidedWith.remove();
						return;
					}
				}
				DestroyedByMobComponent dbm = (DestroyedByMobComponent)mover.getComponent(DestroyedByMobComponent.class);
				if (dbm != null) {
					mover.remove(); // todo - death sequence
					return;
				}
			}
		}

		// Mob moving into player
		{
			MobComponent mob = (MobComponent)mover.getComponent(MobComponent.class);
			if (mob != null) {
				DestroyedByMobComponent dbm = (DestroyedByMobComponent)results.collidedWith.getComponent(DestroyedByMobComponent.class);
				if (dbm != null) {
					results.collidedWith.remove();
				}
			}
		}

		{
			// Collecting - player moves into collectable
			CanCollectComponent ccc = (CanCollectComponent)mover.getComponent(CanCollectComponent.class);
			if (ccc != null) {
				CollectableComponent cc = (CollectableComponent)results.collidedWith.getComponent(CollectableComponent.class);
				if (cc != null) {
					game.collectorSystem.entityCollected(mover, results.collidedWith);
				}
			}
		}

		{
			// Collecting - collectable moves into player
			CanCollectComponent ccc = (CanCollectComponent)results.collidedWith.getComponent(CanCollectComponent.class);
			if (ccc != null) {
				CollectableComponent cc = (CollectableComponent)mover.getComponent(CollectableComponent.class);
				if (cc != null) {
					game.collectorSystem.entityCollected(results.collidedWith, mover);
				}
			}
		}
	}

}
