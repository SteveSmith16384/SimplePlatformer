package com.mygdx.game.systems;

import com.mygdx.game.components.MovingPlatformComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class MovingPlatformSystem extends AbstractSystem {

	public MovingPlatformSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		MovingPlatformComponent mpc = (MovingPlatformComponent)entity.getComponent(MovingPlatformComponent.class);
		// todo
	}

}
