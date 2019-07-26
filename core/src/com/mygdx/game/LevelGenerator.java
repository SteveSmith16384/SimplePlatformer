package com.mygdx.game;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.lang.NumberFunctions;

public class LevelGenerator {

	private EntityFactory entityFactory;
	private BasicECS ecs;
	//public Point playerStartPos; // todo - multiple

	public LevelGenerator(EntityFactory _entityFactory, BasicECS _ecs) {
		entityFactory = _entityFactory;
		ecs = _ecs;
	}


	public void createLevel1() {
		//playerStartPos = new Point(50, Settings.LOGICAL_HEIGHT_PIXELS-100);

		//if (Settings.RELEASE_MODE) {
		AbstractEntity background = this.entityFactory.createImage("background3.jpg", 0, 0, Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS, -99);
		ecs.addEntity(background);
		//}

		//AbstractEntity floor = this.entityFactory.createWall(20, 20, Settings.LOGICAL_WIDTH_PIXELS-50, 20);
		//ecs.addEntity(floor);

		//int row = Settings.LOGICAL_HEIGHT_PIXELS/2;
		for (int row=100 ; row<Settings.LOGICAL_HEIGHT_PIXELS-Settings.PLATFORM_START_HEIGHT ; row += Settings.PLATFORM_SPACING)
		{
			this.generateRow(row);
		}

		/*AbstractEntity edgeUp = this.entityFactory.createEdge(50, 20, 300, 50);
		ecs.addEntity(edgeUp);

		AbstractEntity edgeDown = this.entityFactory.createEdge(300, 50, 400, 500);
		ecs.addEntity(edgeDown);*/

	}


	public void generateRow(int row) {
		//generateRow_OneLongRow(row);
		generateRow_MultiplePlatforms(row, NumberFunctions.rnd(1, 5));
	}


	private void generateRow_OneLongRow(int row) {
		int inset = 100;
		int width = Settings.LOGICAL_WIDTH_PIXELS-(inset*2);

		AbstractEntity platform = this.entityFactory.createFluidPlatform(inset, row, width);
		ecs.addEntity(platform);
		AbstractEntity platformImage = this.entityFactory.createPlatformImage1(inset, row, width, 35);
		ecs.addEntity(platformImage);

		AbstractEntity mob = this.entityFactory.createMob1(NumberFunctions.rnd(inset+20, inset+width-20), row);
		ecs.addEntity(mob);

		for (int col=inset ; col<inset+width ; col+=150) {
			AbstractEntity coin = this.entityFactory.createCoin(inset, row+5);
			ecs.addEntity(coin);
		}

	}


	private void generateRow_MultiplePlatforms(int row, int numPlatforms) {
		for (int p=0 ; p<numPlatforms ; p++) {
			if (numPlatforms > 1) {
				if (NumberFunctions.rnd(1,  3) == 1) {
					continue; // Miss some out
				}
			}
			int width = Settings.LOGICAL_WIDTH_PIXELS / (numPlatforms*2);
			int inset = (width/2)+(p*width*2);
			this.createPlatform(row, inset, width, numPlatforms <= 4);
		}
	}


	private void createPlatform(int row, int inset, int width, boolean createMob) {
		AbstractEntity platform = this.entityFactory.createFluidPlatform(inset, row, width);
		ecs.addEntity(platform);
		AbstractEntity platformImage = this.entityFactory.createPlatformImage1(inset, row, width, 35);
		ecs.addEntity(platformImage);

		if (createMob) {
			AbstractEntity mob = this.entityFactory.createMob1(NumberFunctions.rnd(inset+20, inset+width-20), row);
			ecs.addEntity(mob);
		}

		for (int col=inset ; col<inset+width ; col+=100) {
			AbstractEntity coin = this.entityFactory.createCoin(col, row+5);
			ecs.addEntity(coin);
		}

	}

}
