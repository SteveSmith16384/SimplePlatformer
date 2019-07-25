package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.Settings;
import com.mygdx.game.components.MoveDownComponent;
import com.mygdx.game.components.MoveOffScreenComponent;
import com.mygdx.game.components.PositionComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class MoveDownSystem extends AbstractSystem {

	public MoveDownSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public Class getEntityClass() {
		return MoveDownComponent.class;
	}

	
	@Override
	public void processEntity(AbstractEntity entity) {
		MoveDownComponent gic = (MoveDownComponent)entity.getComponent(MoveDownComponent.class);
		if (gic != null) {
			PositionComponent pos = (PositionComponent)entity.getComponent(PositionComponent.class);
			if (pos.rect.top < 0) {
				entity.remove();
				return;
			}
			pos.rect.move(0, -30 * Gdx.graphics.getDeltaTime());

		}
	}

}
