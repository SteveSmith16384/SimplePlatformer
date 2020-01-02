package com.mygdx.game.systems;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.CanBeHarmedComponent;
import com.mygdx.game.components.CanCollectComponent;
import com.mygdx.game.components.CollectableComponent;
import com.mygdx.game.components.HarmOnContactComponent;
import com.mygdx.game.components.KillByJumpingOnComponent;
import com.mygdx.game.components.MobComponent;
import com.mygdx.game.components.PlayersAvatarComponent;
import com.mygdx.game.models.CollisionResults;
import com.mygdx.game.models.PlayerData;
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
					KillByJumpingOnComponent kbj = (KillByJumpingOnComponent)mover.getComponent(KillByJumpingOnComponent.class);
					if (kbj != null) {
						results.collidedWith.remove();
						game.sfx.play("Laser.ogg");
						AbstractEntity fall = game.entityFactory.createFallingMob(results.collidedWith);
						game.ecs.addEntity(fall);

						// Give player points
						PlayersAvatarComponent uic = (PlayersAvatarComponent)mover.getComponent(PlayersAvatarComponent.class);
						if (uic != null) {
							uic.player.score += 200;
						}

						return;
					}
				}
				PlayersAvatarComponent dbm = (PlayersAvatarComponent)mover.getComponent(PlayersAvatarComponent.class);
				if (dbm != null) {
					this.playerKilled(mover, dbm.timeStarted);
					return;

				}
			}
		}

		// Mob moving into player
		{
			MobComponent mob = (MobComponent)mover.getComponent(MobComponent.class);
			if (mob != null) {
				PlayersAvatarComponent dbm = (PlayersAvatarComponent)results.collidedWith.getComponent(PlayersAvatarComponent.class);
				if (dbm != null) {
					this.playerKilled(results.collidedWith, dbm.timeStarted);
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
					mover.remove();
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


	public void playerKilled(AbstractEntity avatar, long timeStarted) {
		long diff = System.currentTimeMillis() - timeStarted;
		if (diff < 4000) { // Invincible for 4 seconds
			/*if (!Settings.RELEASE_MODE) {
				MyGdxGame.p("Player invincible!");
			}*/
			return;
		}

		game.sfx.play("Falling.mp3");

		avatar.remove();
		game.ecs.addEntity(game.entityFactory.createDeadPlayer(avatar));

		PlayersAvatarComponent uic = (PlayersAvatarComponent)avatar.getComponent(PlayersAvatarComponent.class);
		PlayerData player = uic.player;
		player.avatar = null;
		player.timeUntilAvatar = Settings.AVATAR_RESPAWN_TIME_SECS;
		player.lives--;

	}

}
