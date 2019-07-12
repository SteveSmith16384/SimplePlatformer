package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Settings;
import com.mygdx.game.components.MovementComponent;
import com.mygdx.game.components.PositionData;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class MovementSystem extends AbstractSystem {

	public MovementSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		MovementComponent md = (MovementComponent)entity.getComponent(MovementComponent.class);
		if (md != null) {
			PositionData pos = (PositionData)entity.getComponent(PositionData.class);
			pos.prevPos.set(pos.rect);
			pos.rect.move(md.offX * Gdx.graphics.getDeltaTime(),  md.offY * Gdx.graphics.getDeltaTime());
			md.offX = 0;
			if (md.canFall) {
				// Gravity
				md.offY -= Settings.GRAVITY; // todo - only if can fall!
			} else {
				md.offY = 0;
			}
		}
	}

}
