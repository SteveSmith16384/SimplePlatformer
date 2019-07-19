package com.mygdx.game.systems;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Settings;
import com.mygdx.game.components.MobComponent;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PositionComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class MobAISystem extends AbstractSystem {

	private MyGdxGame game;
	
	public MobAISystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs);
		
		game = _game;
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		MobComponent mob = (MobComponent)entity.getComponent(MobComponent.class);
		if (mob != null) {
			if (mob.dirX == 0) {
				mob.dirX = -1;
			}
			
			PositionComponent pos = (PositionComponent)entity.getComponent(PositionComponent.class);
			// Check for missing floor
			if (game.collisionSystem.getEntityAt(pos.rect.centerX(), pos.rect.bottom-10) == null) {
				mob.dirX = mob.dirX * -1;
				//game.p("Mob turning: " + mob.dirX);
			}
			
			MovementComponent move = (MovementComponent)entity.getComponent(MovementComponent.class);
			move.offX = mob.dirX * Settings.MOB_SPEED;
			
		}
	}

}
