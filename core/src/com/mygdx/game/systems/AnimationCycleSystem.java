package com.mygdx.game.systems;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.components.AnimationCycleComponent;
import com.mygdx.game.components.ImageData;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

/**
 * System for looping an entity through frames
 *
 */
public class AnimationCycleSystem extends AbstractSystem {

	public AnimationCycleSystem(BasicECS ecs) {
		super(ecs);
	}


	@Override
	public void processEntity(AbstractEntity entity) {
		AnimationCycleComponent data = (AnimationCycleComponent)entity.getComponent(AnimationCycleComponent.class);
		if (data != null) {
			data.timeUntilNextFrame -= Gdx.graphics.getDeltaTime();

			if (data.timeUntilNextFrame <= 0) {
				data.currentFrame++;
				if (data.currentFrame >= data.frames.length) {
					data.currentFrame = 0;
				}
				ImageData image = (ImageData)entity.getComponent(ImageData.class);
				image.sprite = data.frames[data.currentFrame];

				data.timeUntilNextFrame = data.interval;
			}
		}
	}

}
