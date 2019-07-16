package com.mygdx.game.systems;

import java.util.Iterator;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.components.BlocksEndOfLevelComponent;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.AbstractSystem;
import com.scs.basicecs.BasicECS;

public class CheckForEndOfLevelSystem extends AbstractSystem {

	private MyGdxGame game;
	
	public CheckForEndOfLevelSystem(MyGdxGame _game, BasicECS ecs) {
		super(ecs);
		
		game = _game;
	}



	public void process() {
		Iterator<AbstractEntity> it = ecs.getIterator();
		while (it.hasNext()) {
			AbstractEntity entity = it.next();
			BlocksEndOfLevelComponent beolc = (BlocksEndOfLevelComponent)entity.getComponent(BlocksEndOfLevelComponent.class);
			if (beolc != null) {
				return;
			}
		}
		
		game.endOfLevel();
	}



}
