package com.mygdx.game;

import com.scs.basicecs.AbstractEntity;
import com.scs.basicecs.BasicECS;
import com.scs.lang.NumberFunctions;

public class LevelGenerator {

	private EntityFactory entityFactory;
	private BasicECS ecs;

	public LevelGenerator(EntityFactory _entityFactory, BasicECS _ecs) {
		entityFactory = _entityFactory;
		ecs = _ecs;
	}


	public void createLevel1() {
		if (Settings.RELEASE_MODE) {
			AbstractEntity background = this.entityFactory.createImage("background.jpg", 0, 0, Settings.LOGICAL_WIDTH_PIXELS, Settings.LOGICAL_HEIGHT_PIXELS, -99);
			ecs.addEntity(background);
		}

		//AbstractEntity floor = this.entityFactory.createWall(20, 20, Settings.LOGICAL_WIDTH_PIXELS-50, 20);
		//ecs.addEntity(floor);

		//int row = Settings.LOGICAL_HEIGHT_PIXELS/2;
		for (int row=Settings.PLATFORM_SPACING ; row<Settings.MAX_PLATFORM_HEIGHT ; row += Settings.PLATFORM_SPACING) {
			this.generateRow(row, false);
		}

		/*AbstractEntity edgeUp = this.entityFactory.createEdge(50, 20, 300, 50);
		ecs.addEntity(edgeUp);

		AbstractEntity edgeDown = this.entityFactory.createEdge(300, 50, 400, 500);
		ecs.addEntity(edgeDown);*/

	}


	public void generateRow(int rowYPos, boolean createMobs) {
		//generateRow_OneLongRow(row);
		
		int numPlatforms = NumberFunctions.rnd(1, 6); // 1;//todo - restore
		generateRow_MultiplePlatforms(rowYPos, numPlatforms, createMobs);
	}

/*
	private void generateRow_OneLongRow(int row) {
		int inset = 100;
		int width = Settings.LOGICAL_WIDTH_PIXELS-(inset*2);

		AbstractEntity platform = this.entityFactory.createFluidPlatform(inset, row, width);
		ecs.addEntity(platform);
		AbstractEntity platformImage = this.entityFactory.createPlatformType1(inset, row, width, 35);
		ecs.addEntity(platformImage);

		AbstractEntity mob = this.entityFactory.createMob1(NumberFunctions.rnd(inset+20, inset+width-20), row);
		ecs.addEntity(mob);

		for (int col=inset ; col<inset+width ; col+=150) {
			AbstractEntity coin = this.entityFactory.createCoin(inset, row+5);
			ecs.addEntity(coin);
		}
	}
*/

	private void generateRow_MultiplePlatforms(int row, int numPlatforms, boolean createMobs) {
		int numCreated = 0;
		for (int p=0 ; p<numPlatforms ; p++) {
			if (numPlatforms > 1) {
				if (numCreated > numPlatforms/2) {
					if (NumberFunctions.rnd(1, 3) == 1) {
						continue; // Miss some out
					}
				}
			}
			int width = Settings.LOGICAL_WIDTH_PIXELS / (numPlatforms*2);
			int startX = (width/2)+(p*width*2);
			this.createPlatform(row, startX, width, createMobs && numPlatforms <= 5);
			numCreated++;
		}
	}


	private void createPlatform(int row, int startX, int width, boolean createMob) {
		AbstractEntity platform = this.entityFactory.createFluidPlatform(startX, row, width);
		ecs.addEntity(platform);
		AbstractEntity platformImage = this.entityFactory.createPlatformType1(startX, row, width, 35);
		ecs.addEntity(platformImage);

		if (createMob) {
			if (NumberFunctions.rnd(1, 3) == 1) {
				AbstractEntity mob = this.entityFactory.createMob_Cannonball(NumberFunctions.rnd(startX+20, startX+width-20), row);
				ecs.addEntity(mob);
			} else {
				AbstractEntity mob = this.entityFactory.createMob1(NumberFunctions.rnd(startX+20, startX+width-60), row);
				ecs.addEntity(mob);
			}
		}

		for (int col=startX+15 ; col<startX+width-15 ; col+=50) {
			/*if (Settings.DEBUG_COINS) {
				MyGdxGame.p("Creating coin");
			}*/
			AbstractEntity coin = this.entityFactory.createCoin(col, row+5);
			ecs.addEntity(coin);
		}

	}

}
