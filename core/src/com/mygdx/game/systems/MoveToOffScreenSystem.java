package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Settings;
import com.mygdx.game.components.MoveOffScreenComponent;
import com.mygdx.game.components.PositionComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class MoveToOffScreenSystem extends AbstractSystem {

	public MoveToOffScreenSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class getEntityClass() {
		return MoveOffScreenComponent.class;
	}

	
	@Override
	public void processEntity(AbstractEntity entity) {
		MoveOffScreenComponent gic = (MoveOffScreenComponent)entity.getComponent(MoveOffScreenComponent.class);
		if (gic != null) {
			PositionComponent pos = (PositionComponent)entity.getComponent(PositionComponent.class);
			if (pos.rect.right < 0 || pos.rect.top < 0 || pos.rect.left > Settings.LOGICAL_WIDTH_PIXELS || pos.rect.bottom > Settings.LOGICAL_HEIGHT_PIXELS) {
				entity.remove();
				return;
			}
			//MovementComponent mc = (MovementComponent)entity.getComponent(MovementComponent.class);
			//mc.offX = gic.offX;
			//mc.offY = gic.offY;
			pos.rect.move(gic.offX * Gdx.graphics.getDeltaTime(), gic.offY * Gdx.graphics.getDeltaTime());

		}
	}

}
