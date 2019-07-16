package com.mygdx.game.systems;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.CanBeHarmedComponent;
import com.mygdx.game.components.CanCollectComponent;
import com.mygdx.game.components.CollectableComponent;
import com.mygdx.game.components.HarmOnContactComponent;
import com.mygdx.game.components.HarmedByMobComponent;
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
				HarmedByMobComponent dbm = (HarmedByMobComponent)mover.getComponent(HarmedByMobComponent.class);
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
				HarmedByMobComponent dbm = (HarmedByMobComponent)results.collidedWith.getComponent(HarmedByMobComponent.class);
				if (dbm != null) {
					results.collidedWith.remove();
					return;
				}
			}
		}

		// Generic harm
		{
			CanBeHarmedComponent cbh = (CanBeHarmedComponent)mover.getComponent(CanBeHarmedComponent.class);
			if (cbh != null) {
				HarmOnContactComponent hoc = (HarmOnContactComponent)results.collidedWith.getComponent(HarmOnContactComponent.class);
				if (hoc != null) {
					mover.remove(); // todo - death effect
					return;
				}
			}
		}

		// Collecting - player moves into collectable
		{
			CanCollectComponent ccc = (CanCollectComponent)mover.getComponent(CanCollectComponent.class);
			if (ccc != null) {
				CollectableComponent cc = (CollectableComponent)results.collidedWith.getComponent(CollectableComponent.class);
				if (cc != null) {
					game.collectorSystem.entityCollected(mover, results.collidedWith);
					return;
				}
			}
		}

		// Collecting - collectable moves into player
		{
			CanCollectComponent ccc = (CanCollectComponent)results.collidedWith.getComponent(CanCollectComponent.class);
			if (ccc != null) {
				CollectableComponent cc = (CollectableComponent)mover.getComponent(CollectableComponent.class);
				if (cc != null) {
					game.collectorSystem.entityCollected(results.collidedWith, mover);
					return;
				}
			}
		}
	}

}
