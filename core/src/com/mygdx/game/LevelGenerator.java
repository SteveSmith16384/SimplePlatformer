package com.mygdx.game;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;

public class LevelGenerator {

	private EntityFactory entityFactory;
	private BasicECS ecs;

	public LevelGenerator(EntityFactory _entityFactory, BasicECS _ecs) {
		entityFactory = _entityFactory;
		ecs = _ecs;
	}


	public void createLevel1() {
		AbstractEntity floor = this.entityFactory.createWall(20, 20, Settings.LOGICAL_WIDTH_PIXELS-50, 20);
		ecs.addEntity(floor);

		for (int row=0 ; row<8 ; row++) {
			AbstractEntity platform = this.entityFactory.createFluidPlatform(50, 100+(row*100), Settings.LOGICAL_WIDTH_PIXELS-150);
			ecs.addEntity(platform);

		/*	AbstractEntity mob = this.entityFactory.createMob1(400, 75+(row*50));
			ecs.addEntity(mob);

			for (int col=0 ; col<10 ; col++) {
				AbstractEntity coin = this.entityFactory.createCoin(50+(row*50), 500);
				ecs.addEntity(coin);
			}
			*/
		}

		/*AbstractEntity edgeUp = this.entityFactory.createEdge(50, 20, 300, 50);
		ecs.addEntity(edgeUp);

		AbstractEntity edgeDown = this.entityFactory.createEdge(300, 50, 400, 500);
		ecs.addEntity(edgeDown);*/

		//AbstractEntity coin = this.entityFactory.createCoin(300, 400);
		//ecs.addEntity(coin);

	}

}
