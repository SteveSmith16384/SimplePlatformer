package com.mygdx.game;

import com.scs.awt.Point;
import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;

public class LevelGenerator {

	private EntityFactory entityFactory;
	private BasicECS ecs;
	public Point playerStartPos; // todo - multiple

	public LevelGenerator(EntityFactory _entityFactory, BasicECS _ecs) {
		entityFactory = _entityFactory;
		ecs = _ecs;
	}


	public void createLevel1() {
		playerStartPos = new Point(50, Settings.LOGICAL_HEIGHT_PIXELS-100);
		
		if (Settings.RELEASE_MODE) {
			AbstractEntity background = this.entityFactory.createImage("background3.jpg", 0, 0, Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS, -99);
			ecs.addEntity(background);
		} else {
			//AbstractEntity test = this.entityFactory.createTestImage(50, 50, 100, 100, -99);
			//ecs.addEntity(test);			
		}

		AbstractEntity floor = this.entityFactory.createWall(20, 20, Settings.LOGICAL_WIDTH_PIXELS-50, 20);
		ecs.addEntity(floor);

		//for (int row=100 ; row<Settings.LOGICAL_HEIGHT_PIXELS-200 ; row+=100)
		int row = Settings.LOGICAL_HEIGHT_PIXELS/2;
		{
			AbstractEntity platform = this.entityFactory.createFluidPlatform(50, row, Settings.LOGICAL_WIDTH_PIXELS-150);
			//ecs.addEntity(platform);
			AbstractEntity platformImage = this.entityFactory.createPlatformImage1(50, row, Settings.LOGICAL_WIDTH_PIXELS-150, 40);
			ecs.addEntity(platformImage);
			
			AbstractEntity mob = this.entityFactory.createMob1(400, row);
			//ecs.addEntity(mob);

			for (int col=0 ; col<10 ; col++) {
				AbstractEntity coin = this.entityFactory.createCoin(50+(col*50), row+5);
				//ecs.addEntity(coin);
			}
		}

		/*AbstractEntity edgeUp = this.entityFactory.createEdge(50, 20, 300, 50);
		ecs.addEntity(edgeUp);

		AbstractEntity edgeDown = this.entityFactory.createEdge(300, 50, 400, 500);
		ecs.addEntity(edgeDown);*/

	}

}
